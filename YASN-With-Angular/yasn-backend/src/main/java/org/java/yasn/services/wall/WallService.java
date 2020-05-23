package org.java.yasn.services.wall;

import java.io.IOException;
import java.util.Collection;

import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.web.models.binding.CommentModel;
import org.java.yasn.web.models.binding.LikeAPostModel;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.CommentResponseModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.springframework.web.multipart.MultipartFile;

public interface WallService {

  WallPostResponseModel createPost(WallPostModel wallPost, MultipartFile[] pictures) throws IOException;

  Collection<WallPostResponseModel> displayAllPosts();

  WallPostServiceModel findWallPostById(String id);

  void likePost(LikeAPostModel likeAPostModel);

  boolean isPostLikedByActiveUser(String postId, String profileId);

  void unlikePost(WallPostServiceModel wallPostServiceModel, String profileUsername);

  CommentResponseModel createComment(CommentModel comment, MultipartFile[] pictures);

}
