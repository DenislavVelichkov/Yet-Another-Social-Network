package org.java.yasn.web.models.binding;

import lombok.Data;

@Data
public class CommentModel {
  public String commentOnPostId;
  public String commentOwnerId;
  public String commentContent;
}
