package org.yasn.services.user;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.web.models.binding.UserLoginBindingModel;

public interface UserService extends UserDetailsService {

  boolean registerUser(UserServiceModel userServiceModel);

  UserServiceModel findUserByUsername(String username);

  UserServiceModel findUserByEmail(String email);

  List<UserServiceModel> findAllUsers();

  void setUserRole(String id, String role);

  Principal loginUser(UserLoginBindingModel loginModel);
}
