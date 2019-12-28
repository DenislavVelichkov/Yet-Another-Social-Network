package org.yasn.services.user;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.yasn.base.TestBase;
import org.yasn.data.entities.user.User;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.repository.user.UserProfileRepository;
import static org.yasn.testConstants.UserAndProfileConstants.*;

public class UserProfileServiceImplTest extends TestBase {

  User userExpected;
  UserProfile userProfileExpected;
  UserServiceModel userServiceModelExpected;
  UserProfileServiceModel userProfileServiceModelExpected;

  @MockBean
  UserProfileRepository profileRepositoryMock;

  @Autowired
  UserProfileService userProfileServiceMock;

  @Autowired
  ModelMapper mapper;

  @Override
  protected void beforeEach() {

    userExpected = new User() {{
      setId(TEST_USER_UUID_EXPECTED);
      setUsername("linus.linux.com");
      setEmail(EMAIL_EXPECTED);
    }};

    userServiceModelExpected = new UserServiceModel() {{
      setId(TEST_USER_UUID_EXPECTED);
      setUsername("linus.linux.com");
      setEmail(EMAIL_EXPECTED);
    }};

    userProfileExpected = new UserProfile() {{
      setProfilePicture(PROFILE_PICTURE_EXPECTED);
      setCoverPicture(COVER_PICTURE_EXPECTED);
      setFullName(FULL_NAME_EXPECTED);
      setProfileOwner(userExpected);
      setFriends(new HashSet<>());
      setId(TEST_PROFILE_UUID_EXPECTED);
      setPostComments(new HashSet<>());
      setWallPosts(new HashSet<>());
      setNotifications(new HashSet<>());
      setFriends(new HashSet<>());
    }};

    userProfileServiceModelExpected = new UserProfileServiceModel() {{
      setProfilePicture(PROFILE_PICTURE_EXPECTED);
      setCoverPicture(COVER_PICTURE_EXPECTED);
      setFullName(FULL_NAME_EXPECTED);
      setProfileOwner(userServiceModelExpected);
      setFriends(new HashSet<>());
      setId(TEST_PROFILE_UUID_EXPECTED);
      setPostComments(new HashSet<>());
      setWallPosts(new HashSet<>());
      setNotifications(new HashSet<>());
      setFriends(new HashSet<>());
    }};

    Mockito.when(this.profileRepositoryMock.findByProfileOwner_Username(USERNAME_EXPECTED))
           .thenReturn(java.util.Optional.ofNullable(this.userProfileExpected));

    Mockito.when(this.profileRepositoryMock.findById(TEST_PROFILE_UUID_EXPECTED))
           .thenReturn(java.util.Optional.ofNullable(this.userProfileExpected));
  }

  @Test
  void userProfileService_ShouldReturnCorrectProfile_OnGivenUsername() {
    UserProfileServiceModel expected = this.userProfileServiceModelExpected;
    UserProfileServiceModel actual =
        this.userProfileServiceMock.findUserProfileByUsername(USERNAME_EXPECTED);

    Assert.assertEquals("FAILED...", expected.getFullName(), actual.getFullName());
    Assert.assertEquals("FAILED...", expected.getCoverPicture(), actual.getCoverPicture());
    Assert.assertEquals("FAILED...", expected.getProfilePicture(), actual.getProfilePicture());
    Assert.assertEquals("FAILED...", expected.getId(), actual.getId());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getId(), actual.getProfileOwner().getId());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getEmail(), actual.getProfileOwner().getEmail());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getUsername(), actual.getProfileOwner().getUsername());
  }

  @Test
  void userProfileService_ShouldReturnCorrectProfile_OnGivenId() {
    UserProfileServiceModel expected = this.userProfileServiceModelExpected;
    UserProfileServiceModel actual =
        this.userProfileServiceMock.findUserProfileById(TEST_PROFILE_UUID_EXPECTED);

    Assert.assertEquals("FAILED...", expected.getFullName(), actual.getFullName());
    Assert.assertEquals("FAILED...", expected.getCoverPicture(), actual.getCoverPicture());
    Assert.assertEquals("FAILED...", expected.getProfilePicture(), actual.getProfilePicture());
    Assert.assertEquals("FAILED...", expected.getId(), actual.getId());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getId(), actual.getProfileOwner().getId());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getEmail(), actual.getProfileOwner().getEmail());
    Assert.assertEquals("FAILED...", expected.getProfileOwner().getUsername(), actual.getProfileOwner().getUsername());
  }


}