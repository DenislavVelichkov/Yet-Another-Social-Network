package org.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.data.models.binding.UserRegisterBindingModel;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.services.user.UserService;
import org.yasn.validation.user.UserRegisterValidator;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;

  @GetMapping("/login")
  public ModelAndView userLogin() {
    return super.view("/user/login");
  }

  @GetMapping("/register")
  @PageTitle("Log In or Sign Up")
  public ModelAndView index(
      @ModelAttribute(name = "registerModel") UserRegisterBindingModel registerModel) {

    return super.view("/user/register");
  }

  @PostMapping("/register")
  public ModelAndView registerConfirm(
      ModelAndView modelAndView,
      @ModelAttribute(name = "model") UserRegisterBindingModel model,
      BindingResult bindingResult) {

    this.userRegisterValidator.validate(model, bindingResult);

    if (bindingResult.hasErrors()) {
      model.setPassword(null);
      model.setConfirmPassword(null);
      modelAndView.addObject("model", model);

      return super.view("index", modelAndView);
    }

    UserServiceModel userServiceModel =
        this.modelMapper.map(model, UserServiceModel.class);
    this.modelMapper.validate();

    this.userService.registerUser(userServiceModel);

    return super.redirect("/user/login");
  }
}
