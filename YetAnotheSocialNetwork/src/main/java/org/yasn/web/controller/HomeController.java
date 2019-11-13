package org.yasn.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.view.AvatarViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.AvatarService;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

  private final UserProfileService userProfileService;
  private final AvatarService avatarService;
  private final WallService wallService;

  @Autowired
  public HomeController(UserProfileService userProfileService,
                        AvatarService avatarService,
                        WallService wallService1) {
    this.userProfileService = userProfileService;
    this.avatarService = avatarService;
    this.wallService = wallService1;
  }

  @GetMapping("/")
  public ModelAndView index() {

    return super.redirect("/user/register");
  }

  @GetMapping("/home")
  @PageTitle("Home")
  public ModelAndView home(ModelAndView modelAndView,
                           @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                           Principal activeUser) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    AvatarViewModel avatarViewModel =
        avatarService.findAvatarByOwnerId(userProfileServiceModel.getId());

    List<WallPostViewModel> allPosts = this.wallService.displayAllPosts();

    /*Required for Thymeleaf to display avatar picture correctly.*/

    modelAndView.addObject("avatar", avatarViewModel);
    modelAndView.addObject("wallPost", wallPost);
    modelAndView.addObject("allPosts", allPosts);

    return super.view("home", modelAndView);
  }
}
