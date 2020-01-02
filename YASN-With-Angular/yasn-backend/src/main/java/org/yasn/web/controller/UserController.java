package org.yasn.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.services.user.UserService;
import org.yasn.validation.user.UserRegisterValidator;
import org.yasn.web.models.binding.UserRegisterBindingModel;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;

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

    isUserRegistered =
        this.userService.registerUser(userServiceModel);
    response.put("isUserRegistered", isUserRegistered);

    return ResponseEntity.ok().body(response);
  }
}

