package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class WallPostViewModel {
  private AvatarViewModel avatar;
  private String postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
}
