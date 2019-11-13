package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.mappings.IHaveCustomMappings;
import org.yasn.service.interfaces.AvatarService;
import org.yasn.utils.FileUtil;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class WallPostViewModel implements IHaveCustomMappings {
  private AvatarViewModel ownerAvatar;
  private String postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;

  private FileUtil fileUtil;
  private AvatarService avatarService;
  private ModelMapper mapper;

  @Autowired
  public WallPostViewModel(final FileUtil fileUtil,final AvatarService avatarService,final ModelMapper mapper) {
    this.fileUtil = fileUtil;
    this.avatarService = avatarService;
    this.mapper = mapper;
  }

  @Override
  public void configureMappings(ModelMapper mapper) {

    Converter<byte[], String> encodePic =
        mappingContext -> mappingContext == null ?
            null : this.fileUtil.encodeByteArrayToBase64String(mappingContext.getSource());
    Converter<UserProfile, AvatarViewModel> swapFields = ctx -> ctx == null ?
        null : this.avatarService.findAvatarByOwnerId(ctx.getSource().getId());

    TypeMap<WallPostServiceModel, WallPostViewModel> viewModelMapping =
        this.mapper.createTypeMap(WallPostServiceModel.class, WallPostViewModel.class)
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
  }
}
