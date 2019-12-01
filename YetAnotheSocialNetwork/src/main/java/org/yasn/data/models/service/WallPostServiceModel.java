package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;

import java.sql.Timestamp;
import java.util.Set;


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
