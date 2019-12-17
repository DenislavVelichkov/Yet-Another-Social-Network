package org.yasn.services.user;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yasn.data.models.service.user.UserServiceModel;

public interface UserService extends UserDetailsService {

  void registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  UserServiceModel findUserByEmail(String email);

  List<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);
}
