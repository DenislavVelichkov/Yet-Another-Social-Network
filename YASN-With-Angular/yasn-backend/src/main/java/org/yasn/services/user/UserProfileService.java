package org.yasn.services.user;

import java.io.IOException;

import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.web.models.binding.ProfileEditBindingModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(String senderId, String recipientUsername);

  boolean editProfile(UserProfileServiceModel userProfile,
                      ProfileEditBindingModel profileEditBindingModel) throws IOException;
}
