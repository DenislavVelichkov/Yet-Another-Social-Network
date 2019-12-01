package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.user.UserProfile;
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
    /*Model mapper tends to assign false values sometimes since Model Mapper v2.3.1 the issue is not fixed*/

    UserProfile profile =
        this.userProfileRepository.findByProfileOwner_Username(username).get();

    UserProfileServiceModel profileService = this.modelMapper.map(profile, UserProfileServiceModel.class);

    profileService.setId(profile.getId());
    profileService.setFullName(profile.getProfileOwner().getFirstName()
        + ' '
        + profile.getProfileOwner().getLastName());

    this.modelMapper.validate();

    return profileService;
  }

  @Override
  public UserProfileServiceModel findUserProfileById(String id) {
    /*Model mapper tends to assign false values sometimes since Model Mapper v2.3.1 the issue is not fixed*/

    UserProfile profile =
        this.userProfileRepository.findById(id).get();

    UserProfileServiceModel profileService =
        this.modelMapper.map(profile, UserProfileServiceModel.class);

    profileService.setId(profile.getId());
    profileService.setFullName(profile.getProfileOwner().getFirstName()
        + ' '
        + profile.getProfileOwner().getLastName());

    this.modelMapper.validate();

    return profileService;
  }


}
