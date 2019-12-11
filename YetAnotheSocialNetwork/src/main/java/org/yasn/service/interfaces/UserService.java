package org.yasn.service.interfaces;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yasn.data.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

  void registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  UserServiceModel findUserByEmail(String email);

  List<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);
}
