package org.yasn.validation.wall;

import org.springframework.validation.Errors;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.validation.ValidationConstants;
import org.yasn.validation.Validator;

@Validator
public class PostValidator implements org.springframework.validation.Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return WallPostBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    WallPostBindingModel postBind = (WallPostBindingModel) o;

    if (postBind.getPostContent().isBlank()) {

      errors.rejectValue(
          "postContent",
          ValidationConstants.POST_IS_EMPTY);
    }
  }
}
