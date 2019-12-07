package org.yasn.service.interfaces;

import org.yasn.data.models.service.UserProfileServiceModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(String senderId, String userName);

  boolean editProfile(UserProfileServiceModel userProfile);
}
