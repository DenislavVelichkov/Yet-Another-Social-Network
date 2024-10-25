package org.java.yasn.services.wall;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

import org.java.yasn.data.models.service.wall.PostCommentServiceModel;

public interface PostCommentService {

  void postComment(PostCommentServiceModel commentModel,
                   Principal user,
                   String postId) throws IOException;

  Collection<PostCommentServiceModel> displayAllPostComments(String id);
}
