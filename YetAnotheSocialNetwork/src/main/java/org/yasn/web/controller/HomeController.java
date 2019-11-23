package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.PostCommentService;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;
import org.yasn.utils.TimeUtilImpl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final ModelMapper modelMapper;
  private final PostCommentService postCommentService;
  private final FileUtil fileUtil;

  @Autowired
  public HomeController(UserProfileService userProfileService,
                        WallService wallService,
                        ModelMapper modelMapper,
                        PostCommentService postCommentService,
                        FileUtil fileUtil) {
    this.userProfileService = userProfileService;
    this.wallService = wallService;
    this.modelMapper = modelMapper;
    this.postCommentService = postCommentService;
    this.fileUtil = fileUtil;
  }

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

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileService, UserProfileViewModel.class);

    // TODO: 11/14/2019 Optimize display with some kind of Cache method
    // TODO: 11/20/2019 Fix Like button

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
    modelAndView.addObject("allWallPosts", allPosts);
    modelAndView.addObject("time", new TimeUtilImpl());

    return super.view("home", modelAndView);
  }
}
