package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.repository.wall.WallPostRepository;
import org.yasn.service.interfaces.UserProfileService;
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
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;
  private final FileUtil fileUtil;

  @Autowired
  public WallServiceImpl(WallPostRepository wallPostRepository,
                         UserProfileService userProfileService,
                         ModelMapper modelMapper,
                         FileUtil fileUtil) {

    this.wallPostRepository = wallPostRepository;
    this.userProfileService = userProfileService;
    this.modelMapper = modelMapper;
    this.fileUtil = fileUtil;
  }

  @Override
  public void createPost(WallPostServiceModel wallPostServiceModel,
                         Principal activeUser) throws IOException {
    // TODO: 11/14/2019 Validations !

    wallPostServiceModel.setPostOwner(
        this.userProfileService.findUserProfileByUsername(activeUser.getName()));

    wallPostServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);

    this.wallPostRepository.saveAndFlush(wallPost);

  }

  @Override
  public List<WallPostServiceModel> displayAllPosts() {

    return this.wallPostRepository
        .findAll()
        .stream()
        .map(wallPost -> this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
  }

}
