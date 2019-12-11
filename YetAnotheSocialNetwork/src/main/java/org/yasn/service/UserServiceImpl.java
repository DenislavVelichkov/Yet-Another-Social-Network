package org.yasn.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.common.WebConstants;
import org.yasn.common.enums.Authorities;
import org.yasn.data.entities.gallery.PersonalGallery;
import org.yasn.data.entities.user.User;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.repository.gallery.PersonalGalleryRepository;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.repository.user.UserRepository;
import org.yasn.service.interfaces.RoleService;
import org.yasn.service.interfaces.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder passwordEncoder;
  private final PersonalGalleryRepository personalGalleryRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND));
  }

  @Override
  public void registerUser(UserServiceModel userServiceModel) {
    this.roleService.seedRolesInDb();

    if (this.userRepository.count() == 0) {
      userServiceModel.setAuthorities(this.roleService.findAllRoles());
    } else {
      userServiceModel.setAuthorities(new LinkedHashSet<>());
      userServiceModel.getAuthorities()
                      .add(this.roleService.findByAuthority(Authorities.USER));
    }

    String[] usernamePrep = userServiceModel.getEmail().split("@");

    // TODO: 12/9/19 Implement random generated username;
    userServiceModel.setUsername(usernamePrep[0] + "." + usernamePrep[1]);
    userServiceModel.setActive(true);
    userServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));
    userServiceModel.setPassword(
        this.passwordEncoder.encode(userServiceModel.getPassword()));


    User user = this.modelMapper.map(userServiceModel, User.class);
    this.modelMapper.validate();

    UserProfile profile = new UserProfile();
    PersonalGallery personalGallery = new PersonalGallery();
    personalGallery.setGalleryOwner(profile);

    this.personalGalleryRepository.saveAndFlush(personalGallery);

    profile.setProfileOwner(user);
    profile.setFullName(userServiceModel.getFirstName()
        +
        " "
        + userServiceModel.getLastName());

    if (userServiceModel.getGender().equals("male")) {
      profile.setProfilePicture(WebConstants.DEFAULT_AVATAR_MALE_IMG_PATH);
    } else if (userServiceModel.getGender().equals("female")) {
      profile.setProfilePicture(WebConstants.DEFAULT_AVATAR_FEMALE_IMG_PATH);
    } else {
      profile.setProfilePicture(WebConstants.DEFAULT_AVATAR_NEUTRAL_IMG_PATH);
    }
    profile.setCoverPicture(WebConstants.DEFAULT_COVER_IMG_PATH);

    user.setUserProfile(profile);

    this.userRepository.saveAndFlush(user);
  }

  @Override
  public UserServiceModel findUserByUsername(String username) {
    return this.userRepository
        .findByUsername(username)
        .map(user -> this.modelMapper.map(user, UserServiceModel.class))
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND));
  }

  @Override
  public UserServiceModel findUserByEmail(String email) {
    return this.userRepository
        .findByEmail(email)
        .map(user -> this.modelMapper.map(user, UserServiceModel.class))
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND));
  }

  @Override
  public List<UserServiceModel> findAllUsers() {
    return this.userRepository
        .findAll()
        .stream()
        .map(user -> this.modelMapper.map(user, UserServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void setUserRole(String id, String role) {
    User user = this.userRepository.findById(id)
                                   .orElseThrow(() ->
                                       new IllegalArgumentException(
                                           ExceptionMessages.INCORRECT_ID));

    UserServiceModel userServiceModel =
        this.modelMapper.map(user, UserServiceModel.class);
    this.modelMapper.validate();

    userServiceModel.getAuthorities().clear();

    switch (role) {
      case "user":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.USER));
        break;
      case "moderator":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.USER));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.MODERATOR));
        break;
      case "admin":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.USER));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.MODERATOR));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(Authorities.ADMIN));
        break;
      default:
        break;
    }

    this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
  }
}
