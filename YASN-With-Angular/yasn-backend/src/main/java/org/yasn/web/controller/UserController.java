package org.yasn.web.controller;

import java.net.URI;
import java.net.URISyntaxException;

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
      BindingResult bindingResult) throws URISyntaxException {

    this.userRegisterValidator.validate(registerModel, bindingResult);

    if (bindingResult.hasErrors()) {
      registerModel.setPassword(null);
      registerModel.setConfirmPassword(null);

      return ResponseEntity.created(new URI("/")).body(registerModel);
    }

    UserServiceModel userServiceModel =
        this.modelMapper.map(registerModel, UserServiceModel.class);
    this.modelMapper.validate();

    boolean isUserRegistered =
        this.userService.registerUser(userServiceModel);

    return ResponseEntity.created(new URI("/")).body(isUserRegistered);
  }
}

