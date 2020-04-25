package org.java.yasn.services.wall;

import java.io.IOException;
import java.util.Collection;

import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.web.models.binding.WallPostModel;

public interface WallService {

  boolean createPost(WallPostModel wallPost) throws IOException;

  Collection<WallPostServiceModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  Collection<WallPostServiceModel> findAllByUsername(String username);

  Collection<WallPostServiceModel> findAllByOwnerId(String ownerId);

  void likePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  boolean isPostLikedByActiveUser(String activeUser, String postId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
