package org.yasn.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.yasn.common.annotations.validation.Validator;
import org.yasn.domain.models.binding.UserRegisterBindingModel;
import org.yasn.repository.UserRepository;
import org.yasn.validation.ValidationConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    Pattern namePattern = Pattern.compile("[A-Z][a-z]+");
    Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}");
    Matcher firstNameMatcher = namePattern.matcher(userRegisterBindingModel.getFirstName());
    Matcher lastNameMatcher = namePattern.matcher(userRegisterBindingModel.getLastName());
    Matcher passwordNameMatcher = passwordPattern.matcher(userRegisterBindingModel.getPassword());

    if (!firstNameMatcher.matches()) {
      errors.rejectValue(
          "firstName",
          ValidationConstants.NAME_ONLY_LETTERS,
          ValidationConstants.NAME_ONLY_LETTERS
      );
    }

    if (!lastNameMatcher.matches()) {
      errors.rejectValue(
          "lastName",
          ValidationConstants.NAME_ONLY_LETTERS,
          ValidationConstants.NAME_ONLY_LETTERS
      );
    }

    if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

    if (!passwordNameMatcher.matches()) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORD_CONDITION,
          ValidationConstants.PASSWORD_CONDITION
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
