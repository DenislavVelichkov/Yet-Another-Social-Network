package org.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.UserRegisterBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.UserServiceModel;
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
@AllArgsConstructor
@RequestMapping("/user")
public class UserController extends BaseController {

  private final UserService userService;
  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final TimeUtil timeUtil;
  private final CloudinaryService cloudinaryService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final UserRegisterValidator userRegisterValidator;
  private final UserEditValidator userEditValidator;

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

  @GetMapping("/profile/{profileId}")
  @PageTitle("Profile")
  public ModelAndView userProfileById(ModelAndView modelAndView,
                                      @PathVariable String profileId,
                                      Principal activeUser) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);
    UserProfileServiceModel activeUserProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    ActiveUserDetails activeUserDetails =
        this.modelMapper.map(activeUserProfileServiceModel, ActiveUserDetails.class);

    activeUserDetails.setFirstName(activeUserProfileServiceModel.getProfileOwner().getFirstName());

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
    modelAndView.addObject("wallPost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("/profile-fragments/profile-guest-page", modelAndView);
  }

  @GetMapping("/profile/timeline/{username}")
  @PageTitle("Profile")
  public ModelAndView userProfileByUsername(ModelAndView modelAndView,
                                            @PathVariable String username) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(username);

    ActiveUserDetails activeUserDetails =
        this.modelMapper.map(userProfileServiceModel, ActiveUserDetails.class);

    activeUserDetails.setFirstName(userProfileServiceModel.getProfileOwner().getFirstName());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    List<WallPostServiceModel> postServiceModels =
        this.wallService.findAllByUsername(username);

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
    modelAndView.addObject("wallPost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("profile", modelAndView);
  }


  @PostMapping("/profile/post")
  public ModelAndView postOnWall(ModelAndView modelAndView,
                                 @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                 @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                                 Principal activeUser) throws IOException {

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

    this.wallService.createPost(wallPostServiceModel, activeUser);

    return super.redirect("/user/profile");
  }

  @PostMapping("/profile/comment")
  public ModelAndView postCommentOnPost(ModelAndView modelAndView,
                                        @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                        @ModelAttribute(name = "postId") String postId,
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

    return super.redirect("/user/profile");
  }
}
