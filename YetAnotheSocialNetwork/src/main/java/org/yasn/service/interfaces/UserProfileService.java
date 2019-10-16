package org.yasn.service.interfaces;

import org.yasn.domain.models.service.UserProfileServiceModel;

public interface UserProfileService {
  UserProfileServiceModel findUserProfileByUsername(String username);
}
