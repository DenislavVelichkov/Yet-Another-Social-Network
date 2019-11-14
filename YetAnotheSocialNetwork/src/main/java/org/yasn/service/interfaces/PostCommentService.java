package org.yasn.service.interfaces;

import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.service.PostCommentServiceModel;

import java.security.Principal;

public interface PostCommentService {
  PostCommentServiceModel postComment(PostCommentBindingModel commentModel, Principal user);
}
