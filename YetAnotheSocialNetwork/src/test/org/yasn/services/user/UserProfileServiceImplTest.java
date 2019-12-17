package org.yasn.services.user;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.yasn.base.TestBase;
import org.yasn.data.entities.user.User;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.repository.user.UserProfileRepository;

public class UserProfileServiceImplTest extends TestBase {

  private static String TEST_UUID_EXPECTED = "c96cf655-c3ae-4300-8634-be21dc1111c3";
  private static String TEST_UUID_ACTUAL = "y11cf111-c3ae-4300-8634-be21dc1111c3";
  private static String PROFILE_PICTURE_EXPECTED = "https://res.cloudinary" +
      ".com/yet-another-social-network/image/upload/v1576531560/yet-another-social-network/test" +
      "/test-pictures_person-1jpeg_m3xebb.jpg";
  private static String PROFILE_PICTURE_ACTUAL = "https://res.cloudinary" +
      ".com/yet-another-social-network/image/upload/v1576531559/yet-another-social-network/test" +
      "/test-pictures_person-2_eqblxz.jpg";
  private static String COVER_PICTURE_EXPECTED = "https://res.cloudinary" +
      ".com/yet-another-social-network/image/upload/v1576531621/yet-another-social-network/test" +
      "/test-pictures_cover-2_rf3bz5.jpg";
  private static String COVER_PICTURE_ACTUAL = "https://res.cloudinary" +
      ".com/yet-another-social-network/image/upload/v1576531586/yet-another-social-network/test" +
      "/test-pictures_test_vf2pca.jpg";
  private static String FULL_NAME_EXPECTED = "Linus Torvalds";
  private static String FULL_NAME_ACTUAL = "Wiz Kalifa";
  private static String USERNAME_EXPECTED = "linus.linux.com";
  private static String USERNAME_ACTUAL = "wiz.aa.aa";
  private static String EMAIL_EXPECTED = "linus@linux.com";
  private static String EMAIL_ACTUAL = "wiz@aa.aa";


  User testUserExpected;
  User testUserActual;
  UserProfile testUserProfileExpected;
  UserProfile testUserProfileActual;

  @MockBean
  UserProfileRepository mockedProfileRepo;

  @Override
  protected void beforeEach() {
    this.testUserExpected = new User() {{
      setUsername("linus.linux.com");
      setEmail(EMAIL_EXPECTED);
    }};

    this.testUserProfileExpected = new UserProfile() {{
      setProfilePicture(PROFILE_PICTURE_EXPECTED);
      setCoverPicture(COVER_PICTURE_EXPECTED);
      setFullName(FULL_NAME_EXPECTED);
      setProfileOwner(testUserExpected);
      setFriends(new HashSet<>());
      setId(TEST_UUID_EXPECTED);
      setPostComments(new HashSet<>());
      setWallPosts(new HashSet<>());
      setNotifications(new HashSet<>());
      setFriends(new HashSet<>());
    }};

    this.testUserActual = new User() {{
      setUsername("wiz.aa.aa");
      setEmail(EMAIL_ACTUAL);
    }};

    this.testUserProfileActual = new UserProfile() {{
      setProfilePicture(PROFILE_PICTURE_ACTUAL);
      setCoverPicture(COVER_PICTURE_ACTUAL);
      setFullName(FULL_NAME_ACTUAL);
      setProfileOwner(testUserActual);
      setFriends(new HashSet<>());
      setId(TEST_UUID_ACTUAL);
      setPostComments(new HashSet<>());
      setWallPosts(new HashSet<>());
      setNotifications(new HashSet<>());
      setFriends(new HashSet<>());
    }};

    Mockito.when(
        this.mockedProfileRepo.findByProfileOwner_Username(USERNAME_EXPECTED))
           .thenReturn(Optional.ofNullable(this.testUserProfileExpected));

  }

  @Test
  void userProfileService_ShouldReturnCorrectProfile_On_Given_Username() {
    this.mockedProfileRepo.saveAndFlush(this.testUserProfileExpected);

    UserProfile expected = this.testUserProfileExpected;
    UserProfile actual = this.mockedProfileRepo.findByProfileOwner_Username(USERNAME_ACTUAL).get();

    Assert.assertEquals("FAILED...", expected.getFullName(), actual.getFullName());
    Assert.assertEquals("FAILED...", expected.getCoverPicture(), actual.getCoverPicture());
    Assert.assertEquals("FAILED...", expected.getProfilePicture(), actual.getProfilePicture());
    Assert.assertEquals("FAILED...", expected.getId(), actual.getId());
    Assert.assertEquals("FAILED...", expected.getProfileOwner(), actual.getProfileOwner());
  }
}