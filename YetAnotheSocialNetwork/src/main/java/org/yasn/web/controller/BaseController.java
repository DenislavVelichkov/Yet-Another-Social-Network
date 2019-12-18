package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.service.gallery.PersonalGalleryServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.data.models.view.ActiveUserDetails;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.gallery.PersonalGalleryViewModel;
import org.yasn.services.AuthenticatedUserService;
import org.yasn.services.gallery.PersonalGalleryService;
import org.yasn.services.user.UserProfileService;
import org.yasn.services.wall.WallService;
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

  protected UserProfileViewModel getUserProfileViewById(String profileId) {

    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileById(profileId);

    UserProfileViewModel userProfileView =
        this.modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);
    this.modelMapper.validate();

    return userProfileView;
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
