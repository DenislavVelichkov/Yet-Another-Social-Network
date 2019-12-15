package org.yasn.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.gallery.PersonalGalleryServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.data.models.service.wall.PostCommentServiceModel;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.data.models.view.gallery.PersonalGalleryViewModel;
import org.yasn.service.CloudinaryService;
import org.yasn.service.gallery.PersonalGalleryService;
import org.yasn.service.user.UserProfileService;
import org.yasn.service.wall.PostCommentService;
import org.yasn.service.wall.WallService;
import org.yasn.utils.TimeUtil;
import org.yasn.validation.user.ProfileEditValidator;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class UserProfileController extends BaseController {

  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final TimeUtil timeUtil;
  private final CloudinaryService cloudinaryService;
  private final PostCommentService postCommentService;
  private final PersonalGalleryService personalGallery;
  private final ModelMapper modelMapper;
  private final ProfileEditValidator profileEditValidator;

  @InitBinder
  public void allowEmptyDateBinding(WebDataBinder binder) {
    // tell spring to set empty values as null instead of empty string.
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping("/timeline/{profileId}")
  @PageTitle("Profile")
  public ModelAndView activeUserTimeline(ModelAndView modelAndView,
                                         @PathVariable String profileId) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

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
    this.modelMapper.validate();

    PersonalGalleryServiceModel galleryServiceModel =
        this.personalGallery.findByOwnerId(profileId);

    PersonalGalleryViewModel galleryViewModel =
        this.modelMapper.map(galleryServiceModel, PersonalGalleryViewModel.class);
    this.modelMapper.validate();

    modelAndView.addObject("gallery", galleryViewModel);
    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails(userProfileView));
    modelAndView.addObject("allProfilePosts", allPosts);
    modelAndView.addObject("postComment", new PostCommentBindingModel());
    modelAndView.addObject("timelinePost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

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
  @PageTitle("Profile")
  public ModelAndView guestProfile(
      ModelAndView modelAndView,
      @PathVariable String profileId,
      Principal activeUser) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    UserProfileServiceModel activeUserProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

    UserProfileViewModel activeUserViewModel =
        this.modelMapper.map(activeUserProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

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
    this.modelMapper.validate();


    PersonalGalleryServiceModel galleryServiceModel =
        this.personalGallery.findByOwnerId(profileId);

    PersonalGalleryViewModel galleryViewModel =
        this.modelMapper.map(galleryServiceModel, PersonalGalleryViewModel.class);
    this.modelMapper.validate();

    modelAndView.addObject("gallery", galleryViewModel);
    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails(activeUserViewModel));
    modelAndView.addObject("allProfilePosts", allPosts);
    modelAndView.addObject("postComment", new PostCommentBindingModel());
    modelAndView.addObject("timelinePost", new WallPostBindingModel());
    modelAndView.addObject("time", timeUtil);

    return super.view("guest-profile", modelAndView);
  }

  @PostMapping("/guest/post")
  public ModelAndView postOnTimeline(
      @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
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

  @PostMapping("/guest/comment")
  public ModelAndView postCommentOnGuestTimelinePost(
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
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

  @PostMapping("/timeline/comment")
  public ModelAndView postCommentOnTimelinePost(
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
      @ModelAttribute(name = "postId") String postId,
      @ModelAttribute(name = "username") String username,
      Principal activeUser) throws IOException {

    String profileId =
        this.userProfileService.findUserProfileByUsername(username).getId();

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
                                  @PathVariable(name = "id") String id) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(id);

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);


    modelAndView.addObject("userProfileView", userProfileView);
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails(userProfileView));
    modelAndView.addObject("profileEdit", new ProfileEditBindingModel());

    return super.view("profile-fragments/profile-edit", modelAndView);
  }

  @PostMapping("/edit/{id}")
  public ModelAndView finalizeEditing(
      ModelAndView modelAndView,
      @ModelAttribute(name = "profileEdit") ProfileEditBindingModel profileEdit,
      @PathVariable(name = "id") String id,
      BindingResult bindingResult) throws IOException {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(id);

    if (super.isMajorProfileEditHappened(profileEdit)) {
      String username =
          userProfileServiceModel.getProfileOwner().getUsername();
      profileEdit.setUsername(username);

      this.profileEditValidator.validate(profileEdit, bindingResult);
    }

    if (bindingResult.hasErrors()) {
      profileEdit.setNewPassword(null);
      profileEdit.setConfirmNewPassword(null);

      UserProfileViewModel userProfileView =
          this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

      modelAndView.addObject("profileEdit", profileEdit);
      modelAndView.addObject("userProfileView", userProfileView);
      modelAndView.addObject("activeUserDetails", super.getActiveUserDetails(userProfileView));

      return super.view("profile-fragments/profile-edit", modelAndView);
    }

    this.userProfileService.editProfile(userProfileServiceModel, profileEdit);

    return super.redirect("/profile/timeline/" + id);
  }

  @GetMapping("/friends")
  public ModelAndView friends() {

    return super.view("friends");
  }

}


