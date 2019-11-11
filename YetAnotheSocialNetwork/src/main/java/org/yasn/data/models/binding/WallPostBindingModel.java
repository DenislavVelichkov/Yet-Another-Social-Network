package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.UserProfile;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class WallPostBindingModel {
  private UserProfile createdBy;
  private byte[] postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
}
