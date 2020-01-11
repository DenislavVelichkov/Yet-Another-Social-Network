package org.java.yasn.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;
import org.java.yasn.data.models.service.user.UserServiceModel;
import org.java.yasn.services.user.UserService;
import org.java.yasn.validation.user.UserRegisterValidator;
import org.java.yasn.web.models.binding.UserRegisterBindingModel;
import org.java.yasn.web.models.binding.UserSendCredentials;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;

  @GetMapping(value = "/principal", produces = "application/json")
  public ResponseEntity<?> user(Principal user, HttpSession session) {

    UserServiceModel userModel =
        this.userService.findUserByUsername(user.getName());
    UserSendCredentials credentials =
        this.modelMapper.map(userModel, UserSendCredentials.class);
    this.modelMapper.validate();

    credentials.setUserProfileId(userModel.getId());

    return ResponseEntity.ok(credentials);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestPart("registerModel") UserRegisterBindingModel registerModel,
      BindingResult bindingResult) {

    boolean isUserRegistered;
    Map<String, Object> response = new LinkedHashMap<>();

    this.userRegisterValidator.validate(registerModel, bindingResult);

    if (bindingResult.hasErrors()) {
      registerModel.setPassword(null);
      registerModel.setConfirmPassword(null);
      response.put("rejectedModel", registerModel);
      response.put("isUserRegistered", false);
      response.put("errors", new ArrayList<>(bindingResult.getAllErrors()));

      return ResponseEntity.ok().body(response);
    }

    UserServiceModel userServiceModel =
        this.modelMapper.map(registerModel, UserServiceModel.class);
    this.modelMapper.validate();
    isUserRegistered = this.userService.registerUser(userServiceModel);
    response.put("isUserRegistered", isUserRegistered);

    return ResponseEntity.ok().body(response);
  }

}

