package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
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

    List<WallPostServiceModel> allPostsServiceModels =
        this.wallPostRepository
            .findAll()
            .stream()
            .map(wallPost -> this.modelMapper.map(wallPost, WallPostServiceModel.class))
            .collect(Collectors.toList());


    return allPostsServiceModels
        .stream()
        .map(wallPostServiceModel -> this.modelMapper.map(wallPostServiceModel, WallPostViewModel.class))
        .collect(Collectors.toList());
  }

}
