package org.yasn.data.models.view;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentViewModel extends BaseViewModel {

  private WallPostViewModel parentPost;
  private UserProfileViewModel commentOwner;
  private String commentContent;
  private String commentPicture;
  private Timestamp createdOn;
}
