package org.yasn.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.yasn.common.annotations.validation.Validator;
import org.yasn.domain.models.binding.UserRegisterBindingModel;
import org.yasn.repository.UserRepository;
import org.yasn.validation.ValidationConstants;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

  private final UserRepository userRepository;

  @Autowired
  public UserRegisterValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegisterBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

    // TODO: 9/29/2019 First And Last Name

    if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

    if (!userRegisterBindingModel.getEmail().equals(userRegisterBindingModel.getConfirmEmail())) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_DOESNT_MATCH,
          ValidationConstants.EMAIL_DOESNT_MATCH
      );
    }

    if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
      errors.rejectValue(
          "email",
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail()),
          String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail())
      );
    }
  }
}
