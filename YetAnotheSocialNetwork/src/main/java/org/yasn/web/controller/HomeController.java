package org.yasn.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.TimeUtil;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final ModelMapper modelMapper;
  private final TimeUtil timeUtil;

  @GetMapping("/")
  public ModelAndView index() {

    return super.redirect("/user/register");
  }

  @GetMapping("/home")
  @PageTitle("Home")
  public ModelAndView home(
      ModelAndView modelAndView,
      @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
      Principal activeUser) {

    System.out.println(this.modelMapper.getConfiguration().isAmbiguityIgnored());
    System.out.println(this.modelMapper.getConfiguration().isFullTypeMatchingRequired());
    System.out.println(this.modelMapper.getConfiguration().isFieldMatchingEnabled());
    System.out.println(this.modelMapper.getConfiguration().isCollectionsMergeEnabled());
    System.out.println(this.modelMapper.getConfiguration().isUseOSGiClassLoaderBridging());
    System.out.println(this.modelMapper.getConfiguration().isDeepCopyEnabled());
    System.out.println(this.modelMapper.getConfiguration().isImplicitMappingEnabled());
    System.out.println(this.modelMapper.getConfiguration().isSkipNullEnabled());

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    this.modelMapper.validate();

    ActiveUserDetails activeUserDetails =
        super.getActiveUserDetails(userProfileView);

    // TODO: 11/14/2019 Optimize display with some kind of Cache method

    List<WallPostServiceModel> allPostsServiceModels = this.wallService.displayAllPosts();

    List<WallPostViewModel> allPostsViewModels =
        allPostsServiceModels.stream()
                             .map(view ->
                                 this.modelMapper.map(
                                     view, WallPostViewModel.class))
                             .collect(Collectors.toList());
    this.modelMapper.validate();

    List<WallPostViewModel> allPostsViewModelsSorted =
        allPostsViewModels.stream().filter(wallPostViewModel ->
        wallPostViewModel
            .getPostPrivacy()
            .equals(PostPrivacy.PUBLIC)
            || wallPostViewModel
            .getPostOwner().getId()
            .equals(
                activeUserDetails.getId())
            || wallPostViewModel
            .getPostOwner()
            .getFriends()
            .stream()
            .anyMatch(
                profile ->
                    profile.getId().equals(activeUserDetails.getId())))
        .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
        .collect(Collectors.toList());

    this.modelMapper.validate();

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject(
        "activeUserDetails", activeUserDetails);
    modelAndView.addObject("allWallPosts", allPostsViewModelsSorted);
    modelAndView.addObject("time", timeUtil);

    return super.view("home", modelAndView);
  }
}
