package org.yasn.validation.wall;

import org.springframework.validation.Errors;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.validation.ValidationConstants;
import org.yasn.validation.Validator;

@Validator
public class CommentValidator implements org.springframework.validation.Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return PostCommentBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    PostCommentBindingModel commentBind = (PostCommentBindingModel) o;

    if (commentBind.getCommentContent().isBlank()) {

      errors.rejectValue(
          "commentContent",
          ValidationConstants.COMMENT_IS_EMPTY);
    }

  }
}
