package org.yasn.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.service.interfaces.UserProfileService;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

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
    profileService.setFullName(
        profile.getProfileOwner().getFirstName()
        + ' '
        + profile.getProfileOwner().getLastName());

    this.modelMapper.validate();

    return profileService;
  }

  @Override
  public void addFriend(String senderId, String userName) {

    UserProfileServiceModel recipient =
        this.modelMapper.map(this.findUserProfileByUsername(userName), UserProfileServiceModel.class);
    this.modelMapper.validate();

    UserProfileServiceModel sender =
        this.modelMapper.map(this.findUserProfileById(senderId), UserProfileServiceModel.class);
    this.modelMapper.validate();

    if (recipient.getFriends().stream().noneMatch(userProfile -> userProfile.getId().equals(sender.getId()))) {
      recipient.getFriends().add(sender);
      this.userProfileRepository.saveAndFlush(this.modelMapper.map(recipient, UserProfile.class));
    }

  }


}
