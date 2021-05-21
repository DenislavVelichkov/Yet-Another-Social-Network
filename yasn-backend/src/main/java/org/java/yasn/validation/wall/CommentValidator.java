package org.java.yasn.validation.wall;

import org.java.yasn.validation.ValidationConstants;
import org.java.yasn.validation.Validator;
import org.java.yasn.web.models.binding.PostCommentModel;
import org.springframework.validation.Errors;

@Validator
public class CommentValidator implements org.springframework.validation.Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return PostCommentModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    PostCommentModel commentBind = (PostCommentModel) o;

    if (commentBind.getCommentContent().isEmpty()) {

      errors.rejectValue(
          "commentContent",
          ValidationConstants.COMMENT_IS_EMPTY,
          ValidationConstants.COMMENT_IS_EMPTY);
    }

  }
}
