package org.yasn.service.interfaces;

import org.yasn.data.models.service.UserProfileServiceModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  void addFriend(String senderId, String userName);
}
