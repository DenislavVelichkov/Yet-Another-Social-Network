package org.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.ActiveUserDetails;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.service.interfaces.*;
import org.yasn.utils.TimeUtil;
import org.yasn.validation.user.UserEditValidator;
import org.yasn.validation.user.UserRegisterValidator;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class UserProfileController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final TimeUtil timeUtil;
  private final CloudinaryService cloudinaryService;
  private final PostCommentService postCommentService;
  private final UserEditValidator userEditValidator;
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;


  @GetMapping("/timeline/{profileId}")
  @PageTitle("Profile")
  public ModelAndView activeUserTimeline(ModelAndView modelAndView,
                                         @PathVariable String profileId) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    ActiveUserDetails activeUserDetails = new ActiveUserDetails();
    activeUserDetails.setId(userProfileServiceModel.getId());
    activeUserDetails.setFirstName(userProfileServiceModel.getProfileOwner().getFirstName());
    activeUserDetails.setProfilePicture(userProfileServiceModel.getProfilePicture());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    List<WallPostServiceModel> postServiceModels =
        this.wallService.findAllByOwnerId(profileId);

    List<WallPostViewModel> allPosts =
        postServiceModels
            .stream()
            .map(wallPostServiceModel ->
                this.modelMapper.map(
                    wallPostServiceModel, WallPostViewModel.class))
            .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
            .collect(Collectors.toList());

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject("activeUserDetails", activeUserDetails);
    modelAndView.addObject("allProfilePosts", allPosts);
    modelAndView.addObject("postComment", new PostCommentBindingModel());
    modelAndView.addObject("timelinePost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("profile", modelAndView);
  }

  @PostMapping("/timeline/post")
  public ModelAndView postOnGuestTimeline(@ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                          @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                                          @ModelAttribute(name = "username") String username,
                                          Principal activeUser) throws IOException {

// TODO: 11/12/2019 Validations
    String profileId =
        this.userProfileService.findUserProfileByUsername(username).getId();

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);

    if (!wallPost.getPostPicture().isEmpty()) {
      wallPostServiceModel.setPostPicture(
          this.cloudinaryService.uploadImage(wallPost.getPostPicture()));
    } else {
      wallPostServiceModel.setPostPicture(null);
    }

    if (wallPostServiceModel.getPostPrivacy() == null) {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    this.wallService.createPost(wallPostServiceModel, activeUser.getName());

    return super.redirect("/profile/timeline/" + profileId);
  }

  @GetMapping("/guest/{profileId}")
  @PageTitle("Profile")
  public ModelAndView guestProfile(ModelAndView modelAndView,
                                   @PathVariable String profileId,
                                   Principal activeUser) {
// TODO: 12/2/19 Create a Timeline

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    UserProfileServiceModel activeUserProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    ActiveUserDetails activeUserDetails = new ActiveUserDetails();
    activeUserDetails.setId(activeUserProfileServiceModel.getId());
    activeUserDetails.setFirstName(activeUserProfileServiceModel.getProfileOwner().getFirstName());
    activeUserDetails.setProfilePicture(activeUserProfileServiceModel.getProfilePicture());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    List<WallPostServiceModel> postServiceModels =
        this.wallService.findAllByOwnerId(profileId);

    List<WallPostViewModel> allPosts =
        postServiceModels
            .stream()
            .map(wallPostServiceModel ->
                this.modelMapper.map(
                    wallPostServiceModel, WallPostViewModel.class))
            .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
            .collect(Collectors.toList());

    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject("activeUserDetails", activeUserDetails);
    modelAndView.addObject("allProfilePosts", allPosts);
    modelAndView.addObject("postComment", new PostCommentBindingModel());
    modelAndView.addObject("timelinePost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("guest-profile", modelAndView);
  }

  @PostMapping("/guest/post")
  public ModelAndView postOnTimeline(@ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                     @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                                     @ModelAttribute(name = "activeUserId") String activeUserId,
                                     @ModelAttribute(name = "profileId") String profileId) throws IOException {

// TODO: 11/12/2019 Validations

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);

    if (!wallPost.getPostPicture().isEmpty()) {
      wallPostServiceModel.setPostPicture(
          this.cloudinaryService.uploadImage(wallPost.getPostPicture()));
    } else {
      wallPostServiceModel.setPostPicture(null);
    }

    if (wallPostServiceModel.getPostPrivacy() == null) {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    String username =
        this.userProfileService.findUserProfileById(activeUserId)
            .getProfileOwner()
            .getUsername();

    this.wallService.createPost(wallPostServiceModel, username);

    return super.redirect("/profile/guest/" + profileId);
  }

  @PostMapping("/timeline/comment")
  public ModelAndView postCommentOnTimelinePost(@ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                                @ModelAttribute(name = "postId") String postId,
                                                @ModelAttribute(name = "username") String username,
                                                Principal activeUser) throws IOException {

    String profileId =
        this.userProfileService.findUserProfileByUsername(username).getId();

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    } else {
      postCommentServiceModel.setCommentPicture(null);
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/profile/timeline/" + profileId);
  }

  @PostMapping("/guest/comment")
  public ModelAndView postCommentOnGuestTimelinePost(@ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                                     @ModelAttribute(name = "postId") String postId,
                                                     @ModelAttribute(name = "profileId") String profileId,
                                                     Principal activeUser) throws IOException {

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    } else {
      postCommentServiceModel.setCommentPicture(null);
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/profile/guest/" + profileId);
  }
}
