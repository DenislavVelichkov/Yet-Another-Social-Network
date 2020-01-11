package org.java.yasn.services.user;

import java.security.Principal;
import java.util.List;

import org.java.yasn.data.models.service.user.UserServiceModel;
import org.java.yasn.web.models.binding.UserLoginBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  boolean registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  UserServiceModel findUserByEmail(String email);

  List<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);

  Principal loginUser(UserLoginBindingModel loginModel);
}
