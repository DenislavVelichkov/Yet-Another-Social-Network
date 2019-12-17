package org.yasn.services.wall;

import java.io.IOException;
import java.util.List;

import org.yasn.data.models.service.wall.WallPostServiceModel;

public interface WallService {

  void createPost(WallPostServiceModel wallPost, String username) throws IOException;

  List<WallPostServiceModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  List<WallPostServiceModel> findAllByUsername(String username);

  List<WallPostServiceModel> findAllByOwnerId(String ownerId);

  void likePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  boolean isPostLikedByActiveUser(String activeUser, String postId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
