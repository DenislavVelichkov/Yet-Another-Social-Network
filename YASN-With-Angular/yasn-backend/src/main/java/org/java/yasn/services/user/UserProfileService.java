package org.java.yasn.services.user;

import java.io.IOException;

import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.web.models.binding.ProfileEditBindingModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(String senderId, String recipientUsername);

  boolean editProfile(UserProfileServiceModel userProfile,
                      ProfileEditBindingModel profileEditBindingModel) throws IOException;
}
