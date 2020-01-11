package org.java.yasn.validation.wall;

import org.java.yasn.validation.ValidationConstants;
import org.java.yasn.validation.Validator;
import org.java.yasn.web.models.binding.WallPostBindingModel;
import org.springframework.validation.Errors;

@Validator
public class PostValidator implements org.springframework.validation.Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return WallPostBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    WallPostBindingModel postBind = (WallPostBindingModel) o;

    if (postBind.getPostContent().isEmpty()) {

      errors.rejectValue(
          "postContent",
          ValidationConstants.POST_IS_EMPTY,
          ValidationConstants.POST_IS_EMPTY);
    }
  }
}
