package org.java.yasn.services.wall;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.data.entities.wall.PostComment;
import org.java.yasn.data.models.service.wall.PostCommentServiceModel;
import org.java.yasn.repository.wall.PostCommentRepository;
import org.java.yasn.services.user.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

  private final PostCommentRepository postCommentRepository;
  private final ModelMapper modelMapper;
  private final UserProfileService userProfileService;
  private final WallService wallService;

  @Override
  public void postComment(PostCommentServiceModel postCommentServiceModel,
                          Principal activeUser, String postId) throws IOException {

    postCommentServiceModel.setCommentOwner(
        this.userProfileService.findUserProfileByUsername(activeUser.getName()));
    postCommentServiceModel.setParentPost(this.wallService.findWallPostById(postId));
    postCommentServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    PostComment postComment =
        this.modelMapper.map(postCommentServiceModel, PostComment.class);
    this.modelMapper.validate();
    postComment.setId(null);

    this.postCommentRepository.saveAndFlush(postComment);
  }

  @Override
  public Collection<PostCommentServiceModel> displayAllPostComments(String id) {
    return this.postCommentRepository
        .findAllByParentPost_Id(id)
        .stream()
        .map(postComment ->
            this.modelMapper.map(
                postComment, PostCommentServiceModel.class))
        .collect(Collectors.toList());
  }
}
