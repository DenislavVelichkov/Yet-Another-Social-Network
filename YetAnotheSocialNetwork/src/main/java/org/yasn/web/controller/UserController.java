package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.domain.models.binding.UserRegisterBindingModel;
import org.yasn.domain.models.service.UserServiceModel;
import org.yasn.service.interfaces.UserService;
import org.yasn.validation.user.UserEditValidator;
import org.yasn.validation.user.UserRegisterValidator;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;
  private final UserEditValidator userEditValidator;

  @Autowired
  public UserController(UserService userService,
                        ModelMapper modelMapper,
                        UserRegisterValidator userRegisterValidator,
                        UserEditValidator userEditValidator) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.userRegisterValidator = userRegisterValidator;
    this.userEditValidator = userEditValidator;
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView registerConfirm(ModelAndView modelAndView,
                                      @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                      BindingResult bindingResult) {
    this.userRegisterValidator.validate(model, bindingResult);

    if (bindingResult.hasErrors()) {
      model.setPassword(null);
      model.setConfirmPassword(null);
      modelAndView.addObject("model", model);

      return super.view("user/register", modelAndView);
    }

    UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
    this.userService.registerUser(userServiceModel);

    return super.redirect("/home");
  }
}
