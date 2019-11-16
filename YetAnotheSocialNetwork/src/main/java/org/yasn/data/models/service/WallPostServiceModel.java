package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class WallPostServiceModel extends BaseServiceModel {
  private UserProfileServiceModel postOwner;
  private byte[] postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
}
