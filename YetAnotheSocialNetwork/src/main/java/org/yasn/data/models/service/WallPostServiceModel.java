package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;

import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class WallPostServiceModel extends BaseServiceModel {
  private UserProfileServiceModel postOwner;
  private PostCommentServiceModel postComments;
  private String postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
  private List<PostCommentServiceModel> comments;
}
