package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentServiceModel extends BaseServiceModel {
  private WallPostServiceModel parentPost;
  private UserProfileServiceModel commentOwner;
  private String commentContent;
  private Timestamp createdOn;
  private long likes;
  private boolean isPostLiked;
  private String commentPicture;
}
