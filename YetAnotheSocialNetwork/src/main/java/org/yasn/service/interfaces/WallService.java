package org.yasn.service.interfaces;

import org.yasn.data.models.service.WallPostServiceModel;

import java.security.Principal;

public interface WallService {
  WallPostServiceModel createPost(WallPostServiceModel wallPost, Principal currentUser);
}
