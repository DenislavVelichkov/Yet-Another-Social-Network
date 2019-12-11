package org.yasn.service.interfaces;

import java.io.IOException;

import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(String senderId, String recipientUsername);

  boolean editProfile(UserProfileServiceModel userProfile,
                      ProfileEditBindingModel profileEditBindingModel) throws IOException;
}
