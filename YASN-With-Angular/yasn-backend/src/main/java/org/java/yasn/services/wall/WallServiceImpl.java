package org.java.yasn.services.wall;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.entities.LikeId;
import org.java.yasn.data.entities.wall.Like;
import org.java.yasn.data.entities.wall.WallPost;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.repository.LikeRepository;
import org.java.yasn.repository.wall.WallPostRepository;
import org.java.yasn.services.AuthenticatedUserService;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.utils.FileUtil;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WallServiceImpl implements WallService {
  private final WallPostRepository wallPostRepository;
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;
  private final LikeRepository likeRepository;
  private final FileUtil fileUtil;
  private final AuthenticatedUserService authUserService;
  private final CloudinaryService cloudinaryService;

  @Override
  public WallPostResponseModel createPost(WallPostModel postModel) throws IOException {
    WallPostServiceModel wallPostServiceModel = new WallPostServiceModel();
    wallPostServiceModel.setPostOwner(
        this.userProfileService.findUserProfileById(postModel.getPostOwnerId()));
    wallPostServiceModel.setPostContent(postModel.getPostContent());
    wallPostServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    if (postModel.getPostPicture() != null) {
      wallPostServiceModel.setPostPicture(cloudinaryService.uploadImage(postModel.getPostPicture()));
    }

    if (postModel.getPostPrivacy() != null) {
      wallPostServiceModel.setPostPrivacy(postModel.getPostPrivacy());
    } else {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);
    this.modelMapper.validate();

    String newPostId = this.wallPostRepository.saveAndFlush(wallPost).getId();

    long likesCount = this.likeRepository
        .findLikesById_PostId(wallPostServiceModel.getId())
        .size();
    WallPostResponseModel response =
        new WallPostResponseModel(newPostId,
            wallPostServiceModel.getPostOwner().getFullName(),
            wallPostServiceModel.getPostOwner().getProfilePicture(),
            wallPostServiceModel.getPostContent(),
            wallPostServiceModel.getCreatedOn(),
            likesCount);

    return response;
  }

  @Override
  public WallPostServiceModel findWallPostById(String id) {
    WallPost post = this.wallPostRepository.findById(id)
                                           .orElseThrow(
                                               () -> new UsernameNotFoundException(
                                                   ExceptionMessages.INCORRECT_ID));
    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(post, WallPostServiceModel.class);

    this.modelMapper.validate();

    return wallPostServiceModel;
  }

  @Override
  public Collection<WallPostServiceModel> findAllByUsername(String username) {
    return this.wallPostRepository
        .findAllByPostOwner_ProfileOwner_Username(username)
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<WallPostServiceModel> findAllByOwnerId(String ownerId) {
    Collection<WallPostServiceModel> allPostsSorted =
        this.wallPostRepository
            .findAllByPostOwner_Id(ownerId)
            .stream()
            .map(wallPost ->
                this.modelMapper.map(wallPost, WallPostServiceModel.class))
            .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()))
            .collect(Collectors.toList());
    this.modelMapper.validate();

    return allPostsSorted;
  }

  @Override
  public Collection<WallPostResponseModel> displayAllPosts() {
    Collection<WallPostServiceModel> allPostsServiceModels = this.wallPostRepository
        .findAll()
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
    this.modelMapper.validate();

    return allPostsServiceModels.stream()
                                .map(p -> {
                                  long likesCount = this.likeRepository
                                      .findLikesById_PostId(p.getId())
                                      .size();

                                  return new WallPostResponseModel(
                                      p.getId(),
                                      p.getPostOwner().getFullName(),
                                      p.getPostOwner().getProfilePicture(),
                                      p.getPostContent(),
                                      p.getCreatedOn(),
                                      likesCount);
                                })
                                .collect(Collectors.toCollection(LinkedList::new));
  }

  public void likePost(String postId, String profileId) {

    Like like = new Like();


    LikeId likeId = new LikeId();
    likeId.setPostId(postId);
    likeId.setProfileId(profileId);

    like.setId(likeId);
    like.setPostAlreadyLiked(true);


    this.likeRepository.saveAndFlush(like);
  }

  @Override
  public void unlikePost(WallPostServiceModel wallPostServiceModel, String activeUser) {

  }

  @Override
  public boolean isPostLikedByActiveUser(String postId, String profileId) {
    Optional<Like> like =
        this.likeRepository.findLikeById_PostIdAndId_ProfileId(postId, profileId);

    return like.isPresent() && like.get().isPostAlreadyLiked();
  }
}
