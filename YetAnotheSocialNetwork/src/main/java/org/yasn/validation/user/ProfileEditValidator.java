package org.yasn.validation.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
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
        .findUserProfileByProfileOwner_Email(((ProfileEditBindingModel) o)
            .getEmail()).orElse(null);

    assert userProfile != null;
    if (!this.bCryptPasswordEncoder.matches(
            profileEditBindingModel.getOldPassword(), userProfile.getProfileOwner().getPassword())) {
      errors.rejectValue(
          "oldPassword",
          ValidationConstants.WRONG_PASSWORD,
          ValidationConstants.WRONG_PASSWORD
      );
    }

    if (profileEditBindingModel.getNewPassword() != null
        && !profileEditBindingModel.getNewPassword().equals(profileEditBindingModel.getConfirmNewPassword())) {
      errors.rejectValue(
          "newPassword",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

    if (!userProfile.getProfileOwner().getEmail().equals(profileEditBindingModel.getEmail())
        && this.userProfileRepository.findUserProfileByProfileOwner_Email(profileEditBindingModel.getEmail()).isPresent()) {
      errors.rejectValue(
          "email",
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, profileEditBindingModel.getEmail()),
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, profileEditBindingModel.getEmail())
      );
    }
  }
}
