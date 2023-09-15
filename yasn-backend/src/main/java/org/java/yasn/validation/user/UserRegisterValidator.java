package org.java.yasn.validation.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.java.yasn.repository.user.UserRepository;
import org.java.yasn.validation.ValidationConstants;
import org.java.yasn.validation.Validator;
import org.java.yasn.web.models.binding.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

  private final UserRepository userRepository;

  @Autowired
  public UserRegisterValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegisterModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserRegisterModel userRegisterModel = (UserRegisterModel) o;

    Pattern namePattern = Pattern.compile("[A-Z][a-z]+");
    Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}");
    Matcher firstNameMatcher = namePattern.matcher(userRegisterModel.getFirstName());
    Matcher lastNameMatcher = namePattern.matcher(userRegisterModel.getLastName());
    Matcher passwordMatcher = passwordPattern.matcher(userRegisterModel.getPassword());

    if (!firstNameMatcher.matches()) {
      errors.rejectValue(
          "firstName",
          ValidationConstants.NAME_ONLY_LETTERS,
          ValidationConstants.NAME_ONLY_LETTERS);
    }

    if (!lastNameMatcher.matches()) {
      errors.rejectValue(
          "lastName",
          ValidationConstants.NAME_ONLY_LETTERS,
          ValidationConstants.NAME_ONLY_LETTERS);
    }

    if (!userRegisterModel.getPassword()
                          .equals(userRegisterModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH);
    }

    if (!passwordMatcher.matches()) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORD_CONDITION,
          ValidationConstants.PASSWORD_CONDITION);


    }

    if (!userRegisterModel.getEmail().equals(userRegisterModel.getConfirmEmail())) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_DOESNT_MATCH,
          ValidationConstants.EMAIL_DOESNT_MATCH);
    }

    if (this.userRepository.findByEmail(userRegisterModel.getEmail()).isPresent()) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_ALREADY_EXISTS,
          ValidationConstants.EMAIL_ALREADY_EXISTS);
    }
  }
}
