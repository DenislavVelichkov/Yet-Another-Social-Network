package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yasn.common.ExceptionMessages;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.service.interfaces.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {
  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public UserProfileServiceImpl(UserProfileRepository userProfileRepository,
                                ModelMapper modelMapper) {
    this.userProfileRepository = userProfileRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public UserProfileServiceModel findUserProfileByUsername(String username) {

    return this.userProfileRepository.findByProfileOwnerUsername(username)
        .map(userProfile -> this.modelMapper.map(userProfile, UserProfileServiceModel.class))
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND));
  }
}
