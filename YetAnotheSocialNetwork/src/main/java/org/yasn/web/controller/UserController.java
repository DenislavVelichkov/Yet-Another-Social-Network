package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.UserRegisterBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.UserServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.UserService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.TimeUtil;
import org.yasn.validation.user.UserEditValidator;
import org.yasn.validation.user.UserRegisterValidator;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  private final UserService userService;
  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final TimeUtil timeUtil;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;
  private final UserEditValidator userEditValidator;

  @Autowired
  public UserController(UserService userService,
                        UserProfileService userProfileService,
                        WallService wallService,
                        TimeUtil timeUtil,
                        ModelMapper modelMapper,
                        UserRegisterValidator userRegisterValidator,
                        UserEditValidator userEditValidator) {
    this.userService = userService;
    this.userProfileService = userProfileService;
    this.wallService = wallService;
    this.timeUtil = timeUtil;
    this.modelMapper = modelMapper;
    this.userRegisterValidator = userRegisterValidator;
    this.userEditValidator = userEditValidator;
  }

  @GetMapping("/login")
  public ModelAndView userLogin() {
    return super.view("/user/login");
  }

  @GetMapping("/register")
  @PageTitle("Log In or Sign Up")
  public ModelAndView index(ModelAndView modelAndView,
                            @ModelAttribute(name = "registerModel") UserRegisterBindingModel registerModel) {

    modelAndView.addObject("registerModel", registerModel);

    return super.view("/user/register");
  }

  @PostMapping("/register")
  public ModelAndView registerConfirm(ModelAndView modelAndView,
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
    this.userService.registerUser(userServiceModel);

    return super.redirect("/user/login");
  }

  @GetMapping("/profile")
  @PageTitle("Profile")
  public ModelAndView userProfile(ModelAndView modelAndView,
                                  Principal activeUser) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    List<WallPostServiceModel> postServiceModels =
        this.wallService.findAllByUsername(activeUser.getName());

    List<WallPostViewModel> allPosts =
        postServiceModels
            .stream()
            .map(wallPostServiceModel -> this.modelMapper.map(wallPostServiceModel, WallPostViewModel.class))
            .collect(Collectors.toList());

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject("allProfilePosts", allPosts);
    modelAndView.addObject("postComment", new PostCommentBindingModel());
    modelAndView.addObject("wallPost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("profile", modelAndView);
  }


}
