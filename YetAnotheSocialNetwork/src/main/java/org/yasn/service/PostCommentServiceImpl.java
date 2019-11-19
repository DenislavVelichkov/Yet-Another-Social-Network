package org.yasn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.repository.wall.PostCommentRepository;
import org.yasn.service.interfaces.PostCommentService;

import java.security.Principal;

@Service
public class PostCommentServiceImpl implements PostCommentService {
  private final PostCommentRepository postCommentRepository;

  @Autowired
  public PostCommentServiceImpl(PostCommentRepository postCommentRepository) {
    this.postCommentRepository = postCommentRepository;
  }

  @Override
  public PostCommentServiceModel postComment(PostCommentServiceModel commentModel, Principal user) {

    return null;
  }
}
