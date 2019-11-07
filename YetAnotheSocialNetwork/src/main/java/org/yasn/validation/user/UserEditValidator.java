package org.yasn.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.yasn.data.entities.user.User;
import org.yasn.data.models.binding.UserEditBindingModel;
import org.yasn.repository.user.UserRepository;
import org.yasn.validation.ValidationConstants;
import org.yasn.validation.Validator;

@Validator
public class UserEditValidator implements org.springframework.validation.Validator {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserEditValidator(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEditBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserEditBindingModel userEditBindingModel = (UserEditBindingModel) o;

    User user = this.userRepository
        .findByUsername(userEditBindingModel
            .getUsername()).orElse(null);

    if (!this.bCryptPasswordEncoder.matches(
        userEditBindingModel.getOldPassword(), user.getPassword())) {
      errors.rejectValue(
          "oldPassword",
          ValidationConstants.WRONG_PASSWORD,
          ValidationConstants.WRONG_PASSWORD
      );
    }

    if (userEditBindingModel.getPassword() != null
        && !userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

    if (!user.getEmail().equals(userEditBindingModel.getEmail())
        && this.userRepository.findByEmail(userEditBindingModel.getEmail()).isPresent()) {
      errors.rejectValue(
          "email",
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail()),
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail())
      );
    }
  }
}
