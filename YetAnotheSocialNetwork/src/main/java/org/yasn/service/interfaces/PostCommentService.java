package org.yasn.service.interfaces;

import org.yasn.data.models.service.PostCommentServiceModel;

import java.security.Principal;
import java.util.List;

public interface PostCommentService {

  void postComment(PostCommentServiceModel commentModel,
                   Principal user,
                   String postId);

  List<PostCommentServiceModel> displayAllPostComments(String id);
}
