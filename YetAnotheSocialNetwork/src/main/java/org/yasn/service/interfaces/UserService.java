package org.yasn.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yasn.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

  UserServiceModel registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  List<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);
}
