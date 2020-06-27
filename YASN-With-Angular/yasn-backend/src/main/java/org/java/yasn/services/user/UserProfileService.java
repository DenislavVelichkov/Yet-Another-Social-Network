package org.java.yasn.services.user;

import java.io.IOException;

import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.binding.ProfileEditModel;
import org.java.yasn.web.models.response.SearchResultModel;

public interface UserProfileService {

  UserProfileServiceModel findUserProfileByUsername(String username);

  UserProfileServiceModel findUserProfileById(String id);

  boolean addFriend(ActionModel actionModel);

  boolean editProfile(UserProfileServiceModel userProfile,
                      ProfileEditModel profileEditModel) throws IOException;

  SearchResultModel searchForUserProfile(String searchParams);

  boolean checkFriendship(String viewerId, String selectedProfileId);
}
