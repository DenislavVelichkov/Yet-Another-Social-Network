package org.yasn.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.data.entities.LikeId;
import org.yasn.data.entities.wall.Like;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.repository.wall.LikeRepository;
import org.yasn.repository.wall.WallPostRepository;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;

@Service
@AllArgsConstructor
public class WallServiceImpl implements WallService {
  private final WallPostRepository wallPostRepository;
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;
  private final LikeRepository likeRepository;
  private final FileUtil fileUtil;

  @Override
  public void createPost(WallPostServiceModel wallPostServiceModel, String username) {
    // TODO: 11/14/2019 Validations !

    wallPostServiceModel.setPostOwner(
            this.userProfileService.findUserProfileByUsername(username));

    wallPostServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    WallPost wallPost =
            this.modelMapper.map(wallPostServiceModel, WallPost.class);

    this.wallPostRepository.saveAndFlush(wallPost);
  }

  @Override
  public WallPostServiceModel findWallPostById(String id) {
    return this.modelMapper.map(
            this.wallPostRepository.findById(id)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(ExceptionMessages.INCORRECT_ID)),
            WallPostServiceModel.class);
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

    return this.wallPostRepository
            .findAllByPostOwner_Id(ownerId)
            .stream()
            .map(wallPost ->
                    this.modelMapper.map(wallPost, WallPostServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public List<WallPostServiceModel> displayAllPosts() {

    return this.wallPostRepository
            .findAll()
            .stream()
            .map(wallPost ->
                    this.modelMapper.map(wallPost, WallPostServiceModel.class))
            .collect(Collectors.toList());
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
    like.setLiked(true);

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
