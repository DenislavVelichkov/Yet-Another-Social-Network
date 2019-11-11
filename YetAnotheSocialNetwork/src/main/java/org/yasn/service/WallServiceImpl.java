package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.wall.WallPost;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.repository.wall.WallPostRepository;
import org.yasn.service.interfaces.WallService;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class WallServiceImpl implements WallService {
  private final WallPostRepository wallPostRepository;
  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public WallServiceImpl(WallPostRepository wallPostRepository,
                         UserProfileRepository userProfileRepository,
                         ModelMapper modelMapper) {
    this.wallPostRepository = wallPostRepository;
    this.userProfileRepository = userProfileRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public WallPostServiceModel createPost(WallPostServiceModel wallPostServiceModel,
                                         Principal principal) {
    WallPost wallPost = this.modelMapper.map(wallPostServiceModel, WallPost.class);
    wallPost.setCreatedOn(new Timestamp(new Date().getTime()));
    wallPost.setCreatedBy(
        this.userProfileRepository.findByProfileOwnerUsername(principal.getName()).get());

    return this.modelMapper.map(
        this.wallPostRepository.saveAndFlush(wallPost), WallPostServiceModel.class);
  }
}
