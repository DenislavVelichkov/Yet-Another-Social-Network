package org.java.yasn.services.wall;

import java.io.IOException;
import java.util.Collection;

import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.WallPostResponseModel;

public interface WallService {

  boolean createPost(WallPostModel wallPost) throws IOException;

  Collection<WallPostResponseModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  Collection<WallPostServiceModel> findAllByUsername(String username);

  Collection<WallPostServiceModel> findAllByOwnerId(String ownerId);

  void likePost(String postId, String profileId);

  boolean isPostLikedByActiveUser(String postId, String profileId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);
}
