package org.yasn.web.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.view.ActiveUserDetails;
import org.yasn.data.models.view.UserProfileViewModel;


@NoArgsConstructor
public abstract class BaseController {

  protected ModelAndView view(String viewName, ModelAndView modelAndView) {
    modelAndView.setViewName(viewName);

    return modelAndView;
  }

  protected ModelAndView view(String viewName) {
    return this.view(viewName, new ModelAndView());
  }

  protected ModelAndView redirect(String URL) {
    return this.view("redirect:" + URL);
  }

  protected ActiveUserDetails getActiveUserDetails(UserProfileViewModel userProfileView) {
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
}
