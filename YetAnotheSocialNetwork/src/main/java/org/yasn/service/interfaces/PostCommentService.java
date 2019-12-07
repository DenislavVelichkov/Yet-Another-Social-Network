package org.yasn.service.interfaces;

import java.security.Principal;
import java.util.List;

import org.yasn.data.models.service.PostCommentServiceModel;

public interface PostCommentService {

  void postComment(PostCommentServiceModel commentModel,
                   Principal user,
                   String postId);

  List<PostCommentServiceModel> displayAllPostComments(String id);
}
