package org.yasn.services.wall;

import java.io.IOException;
import java.util.List;

import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.data.models.view.WallPostViewModel;

public interface WallService {

  void createPost(WallPostServiceModel wallPost, String username) throws IOException;

  List<WallPostViewModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  List<WallPostServiceModel> findAllByUsername(String username);

  List<WallPostViewModel> findAllByOwnerId(String ownerId);

  void likePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  boolean isPostLikedByActiveUser(String activeUser, String postId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
