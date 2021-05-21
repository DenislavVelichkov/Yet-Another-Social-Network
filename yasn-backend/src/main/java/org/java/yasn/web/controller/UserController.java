package org.java.yasn.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.data.models.service.user.UserServiceModel;
import org.java.yasn.services.user.UserService;
import org.java.yasn.validation.user.UserRegisterValidator;
import org.java.yasn.web.models.binding.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  private final ModelMapper modelMapper;

  private final UserRegisterValidator userRegisterValidator;


  @PostMapping(value = "/register", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> register(
      @RequestPart("registerModel") UserRegisterModel registerModel,
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

      return ResponseEntity.ok(response);
    }

    UserServiceModel userServiceModel =
        this.modelMapper.map(registerModel, UserServiceModel.class);
    this.modelMapper.validate();
    isUserRegistered = this.userService.registerUser(userServiceModel);
    response.put("isUserRegistered", isUserRegistered);

    return ResponseEntity.ok(response);
  }

}

