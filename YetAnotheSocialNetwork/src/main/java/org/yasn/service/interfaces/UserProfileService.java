package org.yasn.service.interfaces;

import org.yasn.data.models.service.UserProfileServiceModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

//  void updateProfile(UserProfileServiceModel userProfile, String username);
}
