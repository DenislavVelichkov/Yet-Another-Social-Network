package org.yasn.data.models.service.wall;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.action.LikeServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;


@Getter
@Setter
@NoArgsConstructor
public class WallPostServiceModel extends BaseServiceModel {
  private UserProfileServiceModel postOwner;
  private String postPicture;
  private String postContent;
  private Set<PostCommentServiceModel> comments;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
  private Set<LikeServiceModel> actualLikes;
  private String location;
}
