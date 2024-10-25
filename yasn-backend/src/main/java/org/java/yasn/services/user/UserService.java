package org.java.yasn.services.user;

import java.util.Collection;

import org.java.yasn.data.models.service.user.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  boolean registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  UserServiceModel findUserByEmail(String email);

  Collection<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);
}
