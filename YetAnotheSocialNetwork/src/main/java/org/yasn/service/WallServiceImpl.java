package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.repository.wall.WallPostRepository;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.utils.FileUtil;

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
                         Principal activeUser) {
    // TODO: 11/14/2019 Validations !

    wallPostServiceModel.setPostOwner(
        this.userProfileService.findUserProfileByUsername(activeUser.getName()));

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
  public List<WallPostServiceModel> displayAllPosts() {

    return this.wallPostRepository
        .findAll()
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
  }

}
