package org.yasn.service.interfaces;

import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.data.models.service.UserProfileServiceModel;

import java.io.IOException;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(String senderId, String userName);

  boolean editProfile(UserProfileServiceModel userProfile, ProfileEditBindingModel profileEditBindingModel) throws IOException;
}
