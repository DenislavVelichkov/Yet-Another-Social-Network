package org.yasn.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.domain.models.binding.WallPostBindingModel;
import org.yasn.domain.models.service.UserProfileServiceModel;
import org.yasn.domain.models.view.AvatarViewModel;
import org.yasn.service.interfaces.UserProfileService;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {
  private final UserProfileService userProfileService;

  @Autowired
  public HomeController(UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @GetMapping("/")
  @PreAuthorize("isAnonymous()")
  public ModelAndView index() {

    return super.redirect("/user/register");
  }

  @GetMapping("/home")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Home")
  public ModelAndView home(ModelAndView modelAndView,
                           @ModelAttribute(name = "model") WallPostBindingModel wallPost,
                           @ModelAttribute(name = "avatar") AvatarViewModel avatar,
                           Principal principal) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(principal.getName());
    avatar.setFullName(userProfileServiceModel.getFullName());
    avatar.setProfilePicture(userProfileServiceModel.getProfilePicture());

    modelAndView.addObject("model", wallPost);
    modelAndView.addObject("avatar", avatar);

    return super.view("home", modelAndView);
  }
}
