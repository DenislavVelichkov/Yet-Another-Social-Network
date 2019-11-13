package org.yasn.service;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.AvatarViewModel;
import org.yasn.data.models.view.WallPostViewModel;
import org.yasn.repository.user.UserProfileRepository;
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
  private final ModelMapper modelMapper;
  private final FileUtil fileUtil;

  @Autowired
  public WallServiceImpl(WallPostRepository wallPostRepository,
                         UserProfileRepository userProfileRepository,
                         AvatarService avatarService,
                         ModelMapper modelMapper,
                         FileUtil fileUtil) {
    this.wallPostRepository = wallPostRepository;
    this.userProfileRepository = userProfileRepository;
    this.avatarService = avatarService;
    this.modelMapper = modelMapper;
    this.fileUtil = fileUtil;
  }

  @Override
  public WallPostServiceModel createPost(WallPostServiceModel wallPostServiceModel,
                                         Principal principal) throws IOException {
    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);
    wallPost.setCreatedOn(new Timestamp(new Date().getTime()));
    wallPost.setCreatedBy(
        this.userProfileRepository.findByProfileOwnerUsername(principal.getName()).get());


    return this.modelMapper.map(
        this.wallPostRepository.saveAndFlush(wallPost), WallPostServiceModel.class);
  }

  @Override
  public List<WallPostViewModel> displayAllPosts() {
    Converter<byte[], String> encodePic =
        mappingContext -> mappingContext == null ?
            null : this.fileUtil.encodeByteArrayToBase64String(mappingContext.getSource());
    Converter<UserProfile, AvatarViewModel> swapFields = ctx -> ctx == null ?
        null : this.avatarService.findAvatarByOwnerId(ctx.getSource().getId());

    TypeMap<WallPostServiceModel, WallPostViewModel> viewModelMapping =
        this.modelMapper.createTypeMap(WallPostServiceModel.class, WallPostViewModel.class)
            /*.addMapping(ctx -> this.avatarService.findAvatarByOwnerId(ctx.getCreatedBy().getId()),
                (wallPostViewModel, o) ->
                    wallPostViewModel.setOwnerAvatar((AvatarViewModel) o))*/
            .addMappings(exp ->
                exp.using(swapFields)
            .map(WallPostServiceModel::getCreatedBy, WallPostViewModel::setOwnerAvatar))
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
        .map(viewModelMapping::map)
        .collect(Collectors.toList());
  }

}
