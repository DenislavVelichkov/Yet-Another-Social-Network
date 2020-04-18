package org.java.yasn.services.wall;

import java.io.IOException;
import java.util.List;

import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.web.models.binding.WallPostModel;

public interface WallService {

  boolean createPost(WallPostModel wallPost) throws IOException;

  List<WallPostServiceModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  List<WallPostServiceModel> findAllByUsername(String username);

  List<WallPostServiceModel> findAllByOwnerId(String ownerId);

  void likePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  boolean isPostLikedByActiveUser(String activeUser, String postId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
