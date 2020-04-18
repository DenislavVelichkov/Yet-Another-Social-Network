package org.java.yasn.services.wall;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.data.entities.LikeId;
import org.java.yasn.data.entities.wall.Like;
import org.java.yasn.data.entities.wall.WallPost;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.repository.wall.LikeRepository;
import org.java.yasn.repository.wall.WallPostRepository;
import org.java.yasn.services.AuthenticatedUserService;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.utils.FileUtil;
import org.java.yasn.web.models.binding.WallPostModel;
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

  @Override
  public boolean createPost(WallPostModel postModel) {
    WallPostServiceModel wallPostServiceModel = new WallPostServiceModel();
    wallPostServiceModel.setPostOwner(
        this.userProfileService.findUserProfileById(postModel.getPostOwnerId()));
    wallPostServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);
    this.modelMapper.validate();

    try {
      this.wallPostRepository.saveAndFlush(wallPost);

      return true;
    } catch (Exception e) {
      e.printStackTrace();

      return false;
    }
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
  public List<WallPostServiceModel> findAllByUsername(String username) {
    return this.wallPostRepository
        .findAllByPostOwner_ProfileOwner_Username(username)
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<WallPostServiceModel> findAllByOwnerId(String ownerId) {
    List<WallPostServiceModel> allPostsSorted =
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
  public List<WallPostServiceModel> displayAllPosts() {
    List<WallPostServiceModel> allPostsServiceModels = this.wallPostRepository
        .findAll()
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
    this.modelMapper.validate();

    return allPostsServiceModels;
  }

  public void likePost(WallPostServiceModel wallPostServiceModel, String profileId) {
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(profileId);

    Like like = new Like();

    WallPost wallPost =
        this.modelMapper.map(
            wallPostServiceModel, WallPost.class);

    LikeId likeId = new LikeId();
    likeId.setPost(wallPost.getId());
    likeId.setProfile(userProfileServiceModel.getId());

    like.setLikeOwner(wallPost);
    like.setId(likeId);


    this.likeRepository.saveAndFlush(like);
  }

  @Override
  public void unlikePost(WallPostServiceModel wallPostServiceModel, String activeUser) {
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser);

    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);

    Like likeToRemove = wallPost
        .getActualLikes()
        .stream()
        .filter(like ->
            like.getId().getProfile()
                .equals(userProfileServiceModel.getId()))
        .findFirst()
        .get();

// TODO: 12/10/2019 Idea: Optimize likes that instead of removing like,
//  set isLiked to false and make controller return a likes count.

    wallPost.getActualLikes().remove(likeToRemove);

    this.wallPostRepository.saveAndFlush(wallPost);
  }

  @Override
  public boolean isPostLikedByActiveUser(String activeUser, String postId) {
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser);

    return this.likeRepository
        .findById_ProfileAndLikeOwner_Id(userProfileServiceModel.getId(), postId).isPresent();
  }
}
