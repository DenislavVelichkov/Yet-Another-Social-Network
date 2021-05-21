package org.java.yasn.web.models.response;

import java.sql.Timestamp;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WallPostResponseModel {
  private String id;
  private String ownerProfileId;
  private String ownerFullName;
  private String ownerAvatarPictureUrl;
  private String content;
  private Collection<String> postPicture;
  private Timestamp createdOn;
  private long likesCount;
  private Collection<CommentResponseModel> comments;
  private boolean postLikedByCurrentUser;
}
