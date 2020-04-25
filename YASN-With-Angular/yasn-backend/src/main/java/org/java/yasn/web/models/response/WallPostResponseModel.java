package org.java.yasn.web.models.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WallPostResponseModel {
  String id;
  String ownerFullName;
  String ownerAvatarPictureUrl;
  String content;
  Timestamp createdOn;
  long likesCount;
}
