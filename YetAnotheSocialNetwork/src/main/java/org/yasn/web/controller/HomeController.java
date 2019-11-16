package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final ModelMapper modelMapper;
  private final FileUtil fileUtil;

  @Autowired
  public HomeController(UserProfileService userProfileService,
                        WallService wallService,
                        ModelMapper modelMapper,
                        FileUtil fileUtil) {
    this.userProfileService = userProfileService;
    this.wallService = wallService;
    this.modelMapper = modelMapper;
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
                           @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                           Principal activeUser) {

    UserProfileServiceModel userProfileService =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileService, UserProfileViewModel.class);

    userProfileView.setProfilePicture(
        this.fileUtil.encodeByteArrayToBase64String(userProfileService.getProfilePicture()));


    // TODO: 11/14/2019 Optimize display with some kind of Cache method
    List<WallPostViewModel> allPosts = this.wallService
        .displayAllPosts()
        .stream()
        .map(view -> {
          String profilePicture =
              this.fileUtil
                  .encodeByteArrayToBase64String(
                      view.getPostOwner().getProfilePicture());

          String postPicture =
              this.fileUtil
                  .encodeByteArrayToBase64String(
                      view.getPostPicture());

          WallPostViewModel viewModel =
              this.modelMapper.map(view, WallPostViewModel.class);

          viewModel.getPostOwner().setProfilePicture(profilePicture);
          viewModel.setPostPicture(postPicture);

          return viewModel;
        })
        .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
        .collect(Collectors.toList());

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject("wallPost", wallPost);
    modelAndView.addObject("allWallPosts", allPosts);

    return super.view("home", modelAndView);
  }
}
