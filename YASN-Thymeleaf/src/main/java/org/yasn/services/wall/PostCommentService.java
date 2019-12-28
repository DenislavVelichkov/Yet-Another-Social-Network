package org.yasn.services.wall;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.yasn.data.models.service.wall.PostCommentServiceModel;

public interface PostCommentService {

  void postComment(PostCommentServiceModel commentModel,
                   Principal user,
                   String postId) throws IOException;

  List<PostCommentServiceModel> displayAllPostComments(String id);
}
