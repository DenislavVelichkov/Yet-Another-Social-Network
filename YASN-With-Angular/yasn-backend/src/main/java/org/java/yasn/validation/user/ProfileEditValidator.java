package org.java.yasn.validation.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.validation.ValidationConstants;
import org.java.yasn.validation.Validator;
import org.java.yasn.web.models.binding.ProfileEditModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

@Validator
@AllArgsConstructor
public class ProfileEditValidator implements org.springframework.validation.Validator {

  private final UserProfileRepository userProfileRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public boolean supports(Class<?> aClass) {
    return ProfileEditModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ProfileEditModel profileEditModel = (ProfileEditModel) o;

    UserProfile userProfile = this.userProfileRepository
        .findByProfileOwner_Username(((ProfileEditModel) o).getUsername())
        .orElseThrow(() ->
            new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

    Pattern namePattern = Pattern.compile("[A-Z][a-z]+");
    Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}");
    Matcher firstNameMatcher = null;
    Matcher lastNameMatcher = null;
    Matcher passwordMatcher = null;

    if (profileEditModel.getNewPassword() != null
        || profileEditModel.getOldPassword() != null) {
      passwordMatcher =
          passwordPattern.matcher(profileEditModel.getNewPassword());

      if (!passwordMatcher.matches()) {
        errors.rejectValue(
            "newPassword",
            ValidationConstants.PASSWORD_CONDITION,
            ValidationConstants.PASSWORD_CONDITION);
      }

      if (!passwordMatcher.matches()) {
        errors.rejectValue(
            "oldPassword",
            ValidationConstants.PASSWORD_CONDITION,
            ValidationConstants.PASSWORD_CONDITION);
      }

      if (profileEditModel.getNewPassword() != null
          && !profileEditModel.getNewPassword()
                              .equals(profileEditModel
                                         .getConfirmNewPassword())) {
        errors.rejectValue(
            "newPassword",
            ValidationConstants.PASSWORDS_DO_NOT_MATCH,
            ValidationConstants.PASSWORDS_DO_NOT_MATCH);
      }


      if (!this.bCryptPasswordEncoder.matches(
          profileEditModel.getOldPassword(), userProfile.getProfileOwner().getPassword())) {
        errors.rejectValue(
            "oldPassword",
            ValidationConstants.WRONG_PASSWORD,
            ValidationConstants.WRONG_PASSWORD);
      }
    }

    if (profileEditModel.getFirstName() != null) {
      firstNameMatcher =
          namePattern.matcher(profileEditModel.getFirstName());

      if (!firstNameMatcher.matches()) {
        errors.rejectValue(
            "firstName",
            ValidationConstants.NAME_ONLY_LETTERS,
            ValidationConstants.NAME_ONLY_LETTERS);
      }
    }

    if (profileEditModel.getLastName() != null) {
      lastNameMatcher =
          namePattern.matcher(profileEditModel.getLastName());

      if (!lastNameMatcher.matches()) {
        errors.rejectValue(
            "lastName",
            ValidationConstants.NAME_ONLY_LETTERS,
            ValidationConstants.NAME_ONLY_LETTERS);
      }
    }

  }
}
