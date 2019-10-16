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
import org.yasn.utils.FileUtil;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {
  private final UserProfileService userProfileService;
  private final FileUtil fileUtil;

  @Autowired
  public HomeController(UserProfileService userProfileService, FileUtil fileUtil) {
    this.userProfileService = userProfileService;
    this.fileUtil = fileUtil;
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
                           @ModelAttribute(name = "wallPostModel") WallPostBindingModel wallPost,
                           @ModelAttribute(name = "avatar") AvatarViewModel avatar,
                           Principal principal) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(principal.getName());

    avatar.setFullName(userProfileServiceModel.getFullName());
    avatar.setGender(userProfileServiceModel.getProfileOwner().getGender());

    modelAndView
        .getModelMap()
        .addAttribute(
            "profilePicture",
            fileUtil.encodeByteArrayToBase64String(userProfileServiceModel.getProfilePicture()));
    modelAndView.addObject("model", wallPost);
    modelAndView.addObject("avatar", avatar);

    return super.view("home", modelAndView);
  }
}
