package org.yasn.service;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.entities.wall.PostComment;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.AvatarViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.repository.wall.PostCommentRepository;
import org.yasn.repository.wall.WallPostRepository;
import org.yasn.service.interfaces.AvatarService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WallServiceImpl implements WallService {
  private final WallPostRepository wallPostRepository;
  private final UserProfileRepository userProfileRepository;
  private final AvatarService avatarService;
  private final PostCommentRepository postCommentRepository;
  private final ModelMapper modelMapper;
  private final FileUtil fileUtil;

  @Autowired
  public WallServiceImpl(WallPostRepository wallPostRepository,
                         UserProfileRepository userProfileRepository,
                         AvatarService avatarService,
                         PostCommentRepository postCommentRepository,
                         ModelMapper modelMapper,
                         FileUtil fileUtil) {

    this.wallPostRepository = wallPostRepository;
    this.userProfileRepository = userProfileRepository;
    this.avatarService = avatarService;
    this.postCommentRepository = postCommentRepository;
    this.modelMapper = modelMapper;
    this.fileUtil = fileUtil;
  }

  @Override
  public WallPostServiceModel createPost(WallPostServiceModel wallPostServiceModel,
                                         Principal principal) throws IOException {

    // TODO: 11/14/2019 Validations !
    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);
    wallPost.setCreatedOn(new Timestamp(new Date().getTime()));
    wallPost.setCreatedBy(
        this.userProfileRepository.findByProfileOwnerUsername(principal.getName()).get());
    PostComment postComment = new PostComment();
    postComment.setParentPost(wallPost);
    postComment.setPostLiked(false);
    this.postCommentRepository.saveAndFlush(postComment);

    return this.modelMapper.map(
        this.wallPostRepository.saveAndFlush(wallPost), WallPostServiceModel.class);
  }

  @Override
  public List<WallPostViewModel> displayAllPosts() {
    ModelMapper modelMapper = new ModelMapper();

    /*TODO Refoctor code, remove this long peace of code somewhere else*/
    Converter<byte[], String> encodePic =
        mappingContext -> mappingContext == null ?
            null : fileUtil.encodeByteArrayToBase64String(mappingContext.getSource());
    Converter<UserProfile, AvatarViewModel> swapFields = ctx -> ctx == null ?
        null : avatarService.findAvatarByOwnerId(ctx.getSource().getId());

    modelMapper.createTypeMap(WallPostServiceModel.class, WallPostViewModel.class)
        .addMappings(exp ->
            exp.using(swapFields)
                .map(WallPostServiceModel::getCreatedBy, WallPostViewModel::setAvatar))
        .addMappings(exp ->
            exp.using(encodePic)
                .map(WallPostServiceModel::getPostPicture, WallPostViewModel::setPostPicture))
        .addMapping(WallPostServiceModel::getPostContent, WallPostViewModel::setPostContent)
        .addMapping(WallPostServiceModel::getLikes, WallPostViewModel::setLikes)
        .addMapping(WallPostServiceModel::getLocation, WallPostViewModel::setLocation)
        .addMapping(WallPostServiceModel::getCreatedOn, WallPostViewModel::setCreatedOn)
        .addMapping(WallPostServiceModel::getPostPrivacy, WallPostViewModel::setPostPrivacy);

    List<WallPostServiceModel> allPostsServiceModels =
        this.wallPostRepository
            .findAll()
            .stream()
            .map(wallPost -> this.modelMapper.map(wallPost, WallPostServiceModel.class))
            .collect(Collectors.toList());

    return allPostsServiceModels
        .stream()
        .map(modelMapper.getTypeMap(WallPostServiceModel.class, WallPostViewModel.class)::map)
        .collect(Collectors.toList());
  }

}
