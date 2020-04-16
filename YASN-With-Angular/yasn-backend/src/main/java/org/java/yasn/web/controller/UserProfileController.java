package org.java.yasn.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.common.annotations.interceptor.PageTitle;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.data.models.service.wall.PostCommentServiceModel;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.services.wall.PostCommentService;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.validation.user.ProfileEditValidator;
import org.java.yasn.validation.wall.CommentValidator;
import org.java.yasn.validation.wall.PostValidator;
import org.java.yasn.web.models.binding.PostCommentBindingModel;
import org.java.yasn.web.models.binding.ProfileEditBindingModel;
import org.java.yasn.web.models.binding.WallPostBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/user-profile")
@AllArgsConstructor
public class UserProfileController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final CloudinaryService cloudinaryService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final ProfileEditValidator profileEditValidator;
  private final CommentValidator commentValidator;
  private final PostValidator postValidator;

  @InitBinder
  public void allowEmptyDateBinding(WebDataBinder binder) {
    // tell spring to set empty values as null instead of empty string.
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }


  @GetMapping(value = "/{profileId}", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> updateAvatar(
      @PathVariable String profileId) {
    UserProfileServiceModel userProfile = userProfileService.findUserProfileById(profileId);
    Map<String, String> response = new HashMap<>();
    response.put("fullName", userProfile.getFullName());
    response.put("profileAvatarPicture", userProfile.getProfilePicture());
    response.put("profileCoverPicture", userProfile.getCoverPicture());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/timeline/{profileId}")
  @PageTitle("Profile")
  public ModelAndView activeUserTimeline(ModelAndView modelAndView,
                                         @PathVariable String profileId) {

    modelAndView.addObject(
        "gallery", super.getProfileGalleryByOwnerId(profileId));
    modelAndView.addObject(
        "userProfileView", super.getUserProfileView());
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    modelAndView.addObject(
        "allProfilePosts", this.wallService.findAllByOwnerId(profileId));
    modelAndView.addObject(
        "postComment", new PostCommentBindingModel());
    modelAndView.addObject(
        "timelinePost", new WallPostBindingModel());

    return super.view("profile", modelAndView);
  }

  @PostMapping("/timeline/post")
  public ModelAndView postOnGuestTimeline(
      @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
      @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
      @ModelAttribute(name = "username") String username,
      Principal activeUser) throws IOException {

    // TODO: 11/12/2019 Validations
    // TODO: 12/2/19 Create a Timeline Guest Post

    String profileId =
        this.userProfileService.findUserProfileByUsername(username).getId();

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);
    this.modelMapper.validate();

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
  @PageTitle("View Profile as Guest")
  public ModelAndView guestProfile(
      ModelAndView modelAndView,
      @PathVariable String profileId) {

    modelAndView.addObject(
        "gallery", super.getProfileGalleryByOwnerId(profileId));
    modelAndView.addObject(
        "userProfileView", super.getUserProfileViewById(profileId));
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    modelAndView.addObject(
        "allProfilePosts", this.wallService.findAllByOwnerId(profileId));
    modelAndView.addObject("postComment", new PostCommentBindingModel());

    return super.view("guest-profile", modelAndView);
  }

  @PostMapping("/guest/comment")
  public ModelAndView postCommentOnGuestTimelinePost(
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
      @ModelAttribute(name = "postId") String postId,
      @ModelAttribute(name = "profileId") String profileId,
      Principal activeUser,
      BindingResult bindingResult) throws IOException {

    this.commentValidator.validate(postComment, bindingResult);

    if (bindingResult.hasErrors()) {
      return super.redirect("/profile/guest/" + profileId);
    }

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

  @PostMapping("/timeline/comment")
  public ModelAndView postCommentOnTimelinePost(
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
      @ModelAttribute(name = "postId") String postId,
      @ModelAttribute(name = "username") String username,
      Principal activeUser,
      BindingResult bindingResult) throws IOException {

    this.commentValidator.validate(postComment, bindingResult);

    String profileId =
        this.userProfileService.findUserProfileByUsername(username).getId();

    if (bindingResult.hasErrors()) {
      return super.redirect("/profile/guest/" + profileId);
    }

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);
    this.modelMapper.validate();

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    } else {
      postCommentServiceModel.setCommentPicture(null);
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/profile/timeline/" + profileId);
  }

  @GetMapping("/edit/{id}")
  @PageTitle("Profile - Edit")
  public ModelAndView profileEdit(ModelAndView modelAndView,
                                  @PathVariable(name = "id") String profileId) {

    modelAndView.addObject(
        "userProfileView", super.getUserProfileViewById(profileId));
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    modelAndView.addObject("profileEdit", new ProfileEditBindingModel());

    return super.view("profile-fragments/profile-edit", modelAndView);
  }

  @PostMapping("/edit/{id}")
  public ModelAndView finalizeEditing(
      ModelAndView modelAndView,
      @ModelAttribute(name = "profileEdit") ProfileEditBindingModel profileEdit,
      @PathVariable(name = "id") String profileId,
      BindingResult bindingResult) throws IOException {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    if (super.isMajorProfileEditHappened(profileEdit)) {
      String username =
          userProfileServiceModel.getProfileOwner().getUsername();
      profileEdit.setUsername(username);

      this.profileEditValidator.validate(profileEdit, bindingResult);
    }

    if (bindingResult.hasErrors()) {
      profileEdit.setNewPassword(null);
      profileEdit.setConfirmNewPassword(null);

      modelAndView.addObject("profileEdit", profileEdit);
      modelAndView.addObject("userProfileView", super.getUserProfileViewById(profileId));
      modelAndView.addObject("activeUserDetails", super.getActiveUserDetails());

      return super.view("profile-fragments/profile-edit", modelAndView);
    }

    this.userProfileService.editProfile(userProfileServiceModel, profileEdit);

    return super.redirect("/profile/timeline/" + profileId);
  }

  @GetMapping("/friends")
  public ModelAndView friends(ModelAndView modelAndView) {

    modelAndView.addObject(
        "userProfileView", super.getUserProfileView());
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());

    return super.view("friends", modelAndView);
  }
}


