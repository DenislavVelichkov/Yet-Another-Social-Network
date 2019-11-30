package org.yasn.service.interfaces;

import org.yasn.data.models.service.WallPostServiceModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface WallService {

  void createPost(WallPostServiceModel wallPost, Principal currentUser) throws IOException;

  List<WallPostServiceModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  List<WallPostServiceModel> findAllByUsername(String username);

  List<WallPostServiceModel> findAllByOwnerId(String ownerId);

  void likePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  boolean isPostLikedByActiveUser(String activeUser);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
