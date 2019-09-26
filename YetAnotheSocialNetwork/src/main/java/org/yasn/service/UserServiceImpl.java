package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.common.UserRoles;
import org.yasn.domain.entities.User;
import org.yasn.domain.models.service.UserServiceModel;
import org.yasn.repository.UserRepository;
import org.yasn.service.interfaces.RoleService;
import org.yasn.service.interfaces.UserService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         RoleService roleService,
                         ModelMapper modelMapper,
                         BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
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


    User user = this.modelMapper.map(userServiceModel, User.class);
    user.setUsername(userServiceModel.getEmail().split("@")[0]);
    user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

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
