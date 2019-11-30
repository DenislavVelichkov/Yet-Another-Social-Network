package org.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.ActiveUserDetails;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.PostCommentService;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;
import org.yasn.utils.TimeUtil;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final ModelMapper modelMapper;
  private final PostCommentService postCommentService;
  private final FileUtil fileUtil;
  private final TimeUtil timeUtil;

  @GetMapping("/")
  public ModelAndView index() {

    return super.redirect("/user/register");
  }

  @GetMapping("/home")
  @PageTitle("Home")
  public ModelAndView home(ModelAndView modelAndView,
                           @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                           @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                           Principal activeUser) {

    UserProfileServiceModel userProfileService =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    ActiveUserDetails activeUserDetails =
        this.modelMapper.map(userProfileService, ActiveUserDetails.class);

    activeUserDetails.setFirstName(userProfileService.getProfileOwner().getFirstName());


    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileService, UserProfileViewModel.class);

    // TODO: 11/14/2019 Optimize display with some kind of Cache method

    List<WallPostServiceModel> allPostsServiceModels =
        this.wallService
            .displayAllPosts();

    List<WallPostViewModel> allPosts = allPostsServiceModels
        .stream()
        .map(view ->
            this.modelMapper.map(view, WallPostViewModel.class))
        .filter(wallPostViewModel ->
            wallPostViewModel
                .getPostPrivacy()
                .equals(PostPrivacy.PUBLIC)
         || wallPostViewModel
                .getPostOwner()
                .getFriends()
                .contains(this.userProfileService
                    .findUserProfileByUsername(activeUser.getName())))
        .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
        .collect(Collectors.toList());

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject("activeUserDetails", activeUserDetails);
    modelAndView.addObject("allWallPosts", allPosts);
    modelAndView.addObject("time", timeUtil);

    return super.view("home", modelAndView);
  }
}
