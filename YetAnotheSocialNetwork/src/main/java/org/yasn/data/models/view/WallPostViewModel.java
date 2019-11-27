package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.wall.Like;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WallPostViewModel {
  private String id;
  private UserProfileViewModel postOwner;
  private String postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
  private Set<PostCommentViewModel> comments;
  private Set<Like> actualLikes;
}
