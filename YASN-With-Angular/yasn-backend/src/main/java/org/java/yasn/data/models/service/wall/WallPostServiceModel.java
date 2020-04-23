package org.java.yasn.data.models.service.wall;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.models.service.BaseServiceModel;
import org.java.yasn.data.models.service.action.LikeServiceModel;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;


@Getter
@Setter
@NoArgsConstructor
public class WallPostServiceModel extends BaseServiceModel {

  @JsonIgnore
  private UserProfileServiceModel postOwner;

  private String postPicture;

  private String postContent;

  @JsonIgnore
  private Set<PostCommentServiceModel> comments;

  private Timestamp createdOn;

  private PostPrivacy postPrivacy;

  private Set<LikeServiceModel> actualLikes;

  private String location;
}
