package org.yasn.testConstants;

import java.sql.Timestamp;
import java.util.Date;

public class UserAndProfileConstants {
  public static String TEST_PROFILE_UUID_EXPECTED = "c96cf655-c3ae-4300-8634-be21dc1111c3";
  public static String TEST_USER_UUID_EXPECTED = "y11cf111-c3ae-4300-8634-be21dc1111c3";
  public static String PROFILE_PICTURE_EXPECTED = "https://res.cloudinaublic.com/yet-another-social-network/image/upload/v1576531560/yet-another-social-network/teublic /test-pictures_person-1jpeg_m3xebb.jpg";
  public static String PROFILE_PICTURE_ACTUAL = "https://res.cloudinaublic.com/yet-another-social-network/image/upload/v1576531559/yet-another-social-network/teublic /test-pictures_person-2_eqblxz.jpg";
  public static String COVER_PICTURE_EXPECTED = "https://res.cloudinaublic.com/yet-another-social-network/image/upload/v1576531621/yet-another-social-network/teublic /test-pictures_cover-2_rf3bz5.jpg";
  public static String COVER_PICTURE_ACTUAL = "https://res.cloudinaublic.com/yet-another-social-network/image/upload/v1576531586/yet-another-social-network/teublic /test-pictures_test_vf2pca.jpg";
  public static String FULL_NAME_EXPECTED = "Linus Torvalds";
  public static String FULL_NAME_ACTUAL = "Wiz Kalifa";
  public static String USERNAME_EXPECTED = "linus.linux.com";
  public static String USERNAME_ACTUAL = "wiz.aa.aa";
  public static String EMAIL_EXPECTED = "linus@linux.com";
  public static String FIRST_NAME_EXPECTED = "Linus";
  public static String LAST_NAME_EXPECTED = "Torvalds";
  public static String PASSWORD_PLAIN_TEXT_EXPECTED = "Aaaaaaa1";
  public static String GENDER_EXPECTED = "wiz@aa.aa";
  public static String DATE_EXPECTED = "01-12-1975";
  public static Timestamp CREATED_ON_EXPECTED = new Timestamp(new Date().getTime());
  public static boolean IS_ACTIVE_EXPECTED = true;

}
