package org.yasn.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.service.gallery.PersonalGalleryServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.data.models.view.ActiveUserDetails;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.data.models.view.gallery.PersonalGalleryViewModel;
import org.yasn.service.AuthenticatedUserService;
import org.yasn.service.gallery.PersonalGalleryService;
import org.yasn.service.user.UserProfileService;
import org.yasn.service.wall.WallService;
import org.yasn.utils.TimeUtil;

public abstract class BaseController {

  @Autowired
  private AuthenticatedUserService authUserService;

  @Autowired
  private UserProfileService userProfileService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private WallService wallService;

  @Autowired
  private TimeUtil timeUtil;

  @Autowired
  private PersonalGalleryService personalGalleryService;


  protected ModelAndView view(String viewName, ModelAndView modelAndView) {
    modelAndView.setViewName(viewName);
    modelAndView.addObject("time", timeUtil);
    return modelAndView;
  }

  protected ModelAndView view(String viewName) {
    return this.view(viewName, new ModelAndView());
  }

  protected ModelAndView redirect(String URL) {
    return this.view("redirect:" + URL);
  }

  protected ActiveUserDetails getActiveUserDetails() {
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(authUserService.getUsername());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

    ActiveUserDetails activeUserDetails = new ActiveUserDetails();
    activeUserDetails.setId(userProfileView.getId());
    activeUserDetails.setFirstName(userProfileView.getProfileOwner().getFirstName());
    activeUserDetails.setProfilePicture(userProfileView.getProfilePicture());
    activeUserDetails.setNotifications(userProfileView.getNotifications());

    return activeUserDetails;
  }

  protected boolean isMajorProfileEditHappened(ProfileEditBindingModel profileEdit) {
    return profileEdit.getNewPassword() != null
        || profileEdit.getEmail() != null
        || profileEdit.getOldPassword() != null
        || profileEdit.getFirstName() != null
        || profileEdit.getLastName() != null;
  }

  protected UserProfileViewModel getUserProfileView() {
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(authUserService.getUsername());

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

    return userProfileView;
  }

  protected List<WallPostViewModel> getAllPosts() {
    String activeUserId = this.getActiveUserDetails().getId();

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
                    activeUserId)
                || wallPostViewModel
                .getPostOwner()
                .getFriends()
                .stream()
                .anyMatch(
                    profile ->
                        profile.getId().equals(activeUserId)))
                          .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
                          .collect(Collectors.toList());

    this.modelMapper.validate();

    return allPostsViewModelsSorted;
  }

  protected UserProfileViewModel getUserProfileViewById(String profileId) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

    this.modelMapper.validate();

    return userProfileView;
  }

  protected List<WallPostViewModel> getAllPostsByOwnerId(String profileId) {
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

    return allPosts;
  }

  protected PersonalGalleryViewModel getProfileGalleryByOwnerId(String profileId) {
    PersonalGalleryServiceModel galleryServiceModel =
        this.personalGalleryService.findByOwnerId(profileId);

    PersonalGalleryViewModel galleryViewModel =
        this.modelMapper.map(galleryServiceModel, PersonalGalleryViewModel.class);
    this.modelMapper.validate();

    return galleryViewModel;
  }
}
