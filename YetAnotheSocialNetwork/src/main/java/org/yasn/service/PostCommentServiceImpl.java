package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.wall.PostComment;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.repository.wall.PostCommentRepository;
import org.yasn.service.interfaces.CloudinaryService;
import org.yasn.service.interfaces.PostCommentService;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {
  private final PostCommentRepository postCommentRepository;
  private final ModelMapper modelMapper;
  private final UserProfileService userProfileService;
  private final WallService wallService;
  private final CloudinaryService cloudinaryService;

  @Autowired
  public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
                                ModelMapper modelMapper,
                                UserProfileService userProfileService,
                                WallService wallService,
                                CloudinaryService cloudinaryService) {
    this.postCommentRepository = postCommentRepository;
    this.modelMapper = modelMapper;
    this.userProfileService = userProfileService;
    this.wallService = wallService;
    this.cloudinaryService = cloudinaryService;
  }

  @Override
  public void postComment(PostCommentServiceModel postCommentServiceModel,
                          Principal activeUser, String postId) {
    postCommentServiceModel.setCommentOwner(
        this.userProfileService.findUserProfileByUsername(activeUser.getName()));
    postCommentServiceModel.setParentPost(this.wallService.findWallPostById(postId));
    postCommentServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    PostComment postComment =
        this.modelMapper.map(postCommentServiceModel, PostComment.class);

    this.postCommentRepository.saveAndFlush(postComment);
  }

  @Override
  public List<PostCommentServiceModel> displayAllPostComments(String id) {
    return this.postCommentRepository
        .findAllByParentPost_Id(id)
        .stream()
        .map(postComment ->
            this.modelMapper.map(
                postComment, PostCommentServiceModel.class))
        .collect(Collectors.toList());
  }
}
