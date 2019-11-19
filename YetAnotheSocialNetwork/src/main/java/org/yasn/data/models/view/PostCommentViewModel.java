package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentViewModel {
  private WallPostViewModel parentPost;
  private UserProfileViewModel commentOwner;
  private Timestamp createdOn;
  private String commentContent;
  private boolean isPostLiked;
  private String commentPicture;
}
