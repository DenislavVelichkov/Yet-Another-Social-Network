package org.yasn.validation.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.yasn.common.ExceptionMessages;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.binding.ProfileEditBindingModel;
import org.yasn.repository.user.UserProfileRepository;
import org.yasn.validation.ValidationConstants;
import org.yasn.validation.Validator;

@Validator
@AllArgsConstructor
public class ProfileEditValidator implements org.springframework.validation.Validator {

  private final UserProfileRepository userProfileRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public boolean supports(Class<?> aClass) {
    return ProfileEditBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ProfileEditBindingModel profileEditBindingModel = (ProfileEditBindingModel) o;

    UserProfile userProfile = this.userProfileRepository
        .findByProfileOwner_Username(((ProfileEditBindingModel) o).getUsername())
        .orElseThrow(() ->
            new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

    Pattern namePattern = Pattern.compile("[A-Z][a-z]+");
    Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}");
    Matcher firstNameMatcher = null;
    Matcher lastNameMatcher = null;
    Matcher passwordMatcher = null;

    if (profileEditBindingModel.getNewPassword() != null
        || profileEditBindingModel.getOldPassword() != null) {
      passwordMatcher =
          passwordPattern.matcher(profileEditBindingModel.getNewPassword());

      if (!passwordMatcher.matches()) {
        errors.rejectValue(
            "newPassword",
            ValidationConstants.PASSWORD_CONDITION
        );
      }

      if (!passwordMatcher.matches()) {
        errors.rejectValue(
            "oldPassword",
            ValidationConstants.PASSWORD_CONDITION
        );
      }

      if (profileEditBindingModel.getNewPassword() != null
          && !profileEditBindingModel.getNewPassword()
                                     .equals(profileEditBindingModel
                                         .getConfirmNewPassword())) {
        errors.rejectValue(
            "newPassword",
            ValidationConstants.PASSWORDS_DO_NOT_MATCH
        );
      }


      if (!this.bCryptPasswordEncoder.matches(
          profileEditBindingModel.getOldPassword(), userProfile.getProfileOwner().getPassword())) {
        errors.rejectValue(
            "oldPassword",
            ValidationConstants.WRONG_PASSWORD
        );
      }
    }

    if (profileEditBindingModel.getFirstName() != null) {
      firstNameMatcher =
          namePattern.matcher(profileEditBindingModel.getFirstName());

      if (!firstNameMatcher.matches()) {
        errors.rejectValue(
            "firstName",
            ValidationConstants.NAME_ONLY_LETTERS
        );
      }
    }

    if (profileEditBindingModel.getLastName() != null) {
      lastNameMatcher =
          namePattern.matcher(profileEditBindingModel.getLastName());

      if (!lastNameMatcher.matches()) {
        errors.rejectValue(
            "lastName",
            ValidationConstants.NAME_ONLY_LETTERS
        );
      }
    }

  }
}
