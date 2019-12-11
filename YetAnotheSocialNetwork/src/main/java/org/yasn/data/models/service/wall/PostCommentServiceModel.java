package org.yasn.data.models.service.wall;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentServiceModel extends BaseServiceModel {

  private WallPostServiceModel parentPost;
  private UserProfileServiceModel commentOwner;
  private String commentContent;
  private String commentPicture;
  private Timestamp createdOn;
}
