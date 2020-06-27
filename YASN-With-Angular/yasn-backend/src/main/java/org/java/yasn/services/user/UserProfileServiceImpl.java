package org.java.yasn.services.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.user.User;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.data.models.service.user.UserServiceModel;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.repository.user.UserRepository;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.action.NotificationService;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.binding.ProfileEditModel;
import org.java.yasn.web.models.response.SearchResultModel;
import org.java.yasn.web.models.response.UserProfileResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileRepository userProfileRepository;

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  private final BCryptPasswordEncoder passwordEncoder;

  private final CloudinaryService cloudinaryService;

  private final NotificationService notificationService;

  @Override
  public UserProfileServiceModel findUserProfileByUsername(String username) {
    /*Model mapper tends to assign false values sometimes since Model Mapper
    v2.3.1 the issue is not fixed*/

    UserProfile profile =
        this.userProfileRepository.findByProfileOwner_Username(username)
                                  .orElseThrow(() ->
                                      new IllegalArgumentException(
                                          String.format(
                                              ExceptionMessages.PROFILE_DOESNT_EXISTS, username)));

    UserProfileServiceModel profileServiceModel =
        this.modelMapper.map(profile, UserProfileServiceModel.class);

    this.modelMapper.validate();

    return profileServiceModel;
  }

  @Override
  public UserProfileServiceModel findUserProfileById(String id) {

    UserProfile profile =
        this.userProfileRepository.findById(id)
                                  .orElseThrow(
                                      () -> new IllegalArgumentException(
                                          ExceptionMessages.USER_PROFILE_NOT_FOUND));

    UserProfileServiceModel profileServiceModel =
        this.modelMapper.map(profile, UserProfileServiceModel.class);
    this.modelMapper.validate();

    return profileServiceModel;
  }

  @Override
  public Map<String, String> addFriend(ActionModel actionModel) {
    boolean successfulAction;

    UserProfile recipient = this.userProfileRepository
        .findById(actionModel.getRecipientId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));
    UserProfile sender = this.userProfileRepository
        .findById(actionModel.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

    if (sender.getFriends().stream()
              .noneMatch(userProfile -> userProfile.getId().equals(recipient.getId()))
        || sender.getId().equals(recipient.getId())) {

      sender.getFriends().add(recipient);
      recipient.getFriends().add(sender);

      this.userProfileRepository.saveAndFlush(sender);
      successfulAction = true;

    } else {
      successfulAction = false;
    }

    Map<String, String> response = new HashMap<>();
    response.putIfAbsent("actionResult", String.valueOf(successfulAction));

    String notificationId =
        this.notificationService.getNotification(
            actionModel.getSenderId(),
            actionModel.getRecipientId(),
            NotificationType.FRIEND_REQ.getLabel()).getId();

    response.putIfAbsent("notificationId", notificationId);

    return response;
  }

  @Override
  public boolean editProfile(UserProfileServiceModel userProfileServiceModel,
                             ProfileEditModel profileEditModel) throws IOException {

    UserServiceModel userServiceModel = userProfileServiceModel.getProfileOwner();

    if (profileEditModel.getEmail() != null) {
      userServiceModel.setEmail(profileEditModel.getEmail());

      String[] usernamePrep = profileEditModel.getEmail().split("@");
      userServiceModel.setUsername(usernamePrep[0] + "." + usernamePrep[1]);
    }

    if (profileEditModel.getNewPassword() != null) {
      userServiceModel.setPassword(
          this.passwordEncoder.encode(profileEditModel.getNewPassword()));
    }

    if (!profileEditModel.getProfilePicture().isEmpty()) {
      userProfileServiceModel.setProfilePicture(
          this.cloudinaryService.uploadImage(
              profileEditModel.getProfilePicture()));
    }

    if (!profileEditModel.getCoverPicture().isEmpty()) {
      userProfileServiceModel.setCoverPicture(
          this.cloudinaryService.uploadImage(
              profileEditModel.getCoverPicture()));
    }

    if (profileEditModel.getBirthday() != null) {
      SimpleDateFormat formatter =
          new SimpleDateFormat("yyyy-MM-dd");
      try {
        userServiceModel.setBirthday(
            formatter.parse(profileEditModel.getBirthday()));
      } catch (ParseException e) {
        e.printStackTrace();
      }

    }

    if (profileEditModel.getGender() != null) {
      userServiceModel.setGender(profileEditModel.getGender());
    }

    if (profileEditModel.getFirstName() != null
        || profileEditModel.getLastName() != null) {
      userProfileServiceModel.setFullName(
          profileEditModel.getFirstName()
              +
              " "
              + profileEditModel.getLastName());
      userServiceModel.setFirstName(profileEditModel.getFirstName());
      userServiceModel.setLastName(profileEditModel.getLastName());
    }

    userProfileServiceModel.setProfileOwner(userServiceModel);

    UserProfile updatedUserProfile =
        this.modelMapper.map(userProfileServiceModel, UserProfile.class);
    this.modelMapper.validate();

    User user = this.modelMapper.map(userServiceModel, User.class);
    this.modelMapper.validate();

    try {
      this.userProfileRepository.saveAndFlush(updatedUserProfile);
      this.userRepository.saveAndFlush(user);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  public SearchResultModel searchForUserProfile(String searchParams) {

    Collection<UserProfile> searchResult =
        this.userProfileRepository.findUserProfilesByFullNameContaining(searchParams);
    Collection<UserProfileResponseModel> profileResponseModels =
        searchResult.stream()
                    .map(userProfile -> this.modelMapper.map(userProfile, UserProfileResponseModel.class))
                    .collect(Collectors.toCollection(LinkedList::new));
    this.modelMapper.validate();

    return new SearchResultModel<>(profileResponseModels);
  }

  @Override
  public boolean checkFriendship(String viewerId, String selectedProfileId) {
    UserProfile profileToCheck =
        this.userProfileRepository.findById(selectedProfileId)
                                  .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

    return profileToCheck.getFriends()
                         .stream()
                         .anyMatch(userProfile -> userProfile.getId().equalsIgnoreCase(viewerId));
  }
}
