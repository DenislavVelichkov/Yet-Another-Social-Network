package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.common.UserRoles;
import org.yasn.common.WebConstants;
import org.yasn.data.entities.user.User;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.service.UserServiceModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.repository.user.UserRepository;
import org.yasn.service.interfaces.RoleService;
import org.yasn.service.interfaces.UserService;
import org.yasn.utils.FileUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder passwordEncoder;
  private final FileUtil fileUtil;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         UserProfileRepository userProfileRepository,
                         RoleService roleService,
                         ModelMapper modelMapper,
                         BCryptPasswordEncoder passwordEncoder,
                         FileUtil fileUtil) {
    this.userRepository = userRepository;
    this.userProfileRepository = userProfileRepository;
    this.roleService = roleService;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.fileUtil = fileUtil;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND));
  }

  @Override
  public UserServiceModel registerUser(UserServiceModel userServiceModel) {
    this.roleService.seedRolesInDb();

    if (this.userRepository.count() == 0) {
      userServiceModel.setAuthorities(this.roleService.findAllRoles());
    } else {
      userServiceModel.setAuthorities(new LinkedHashSet<>());
      userServiceModel.getAuthorities()
          .add(this.roleService.findByAuthority(UserRoles.USER.toString()));
    }

    String[] usernamePrep = userServiceModel.getEmail().split("@");
    Date date = new Date();

    userServiceModel.setUsername(usernamePrep[0] + "." + usernamePrep[1]);
    userServiceModel.setActive(true);
    userServiceModel.setCreatedOn(new Timestamp(date.getTime()));
    userServiceModel.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

    User user = this.modelMapper.map(userServiceModel, User.class);
    UserProfile profile = new UserProfile();
    profile.setProfileOwner(user);
    profile.setFullName(userServiceModel.getFirstName()
        +
        " "
        + userServiceModel.getLastName());

    try {
      profile.setProfilePicture(
          fileUtil.downloadImageFile(
              new URL(WebConstants.DEFAULT_AVATAR_IMG_PATH)));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    this.userProfileRepository.saveAndFlush(profile);

    return this.modelMapper
        .map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
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
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.INCORRECT_ID));

    UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
    userServiceModel.getAuthorities().clear();

    switch (role) {
      case "user":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.USER.toString()));
        break;
      case "moderator":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.USER.toString()));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.MODERATOR.toString()));
        break;
      case "admin":
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.USER.toString()));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.MODERATOR.toString()));
        userServiceModel
            .getAuthorities()
            .add(this.roleService.findByAuthority(UserRoles.ADMIN.toString()));
        break;
      default:
        break;
    }

    this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
  }
}
