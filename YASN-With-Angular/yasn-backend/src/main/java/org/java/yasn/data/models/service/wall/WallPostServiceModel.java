package org.java.yasn.data.models.service.wall;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.models.service.BaseServiceModel;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;


@Getter
@Setter
@NoArgsConstructor
public class WallPostServiceModel extends BaseServiceModel {

  @JsonIgnore
  private UserProfileServiceModel postOwner;

  private String postContent;

  private Timestamp createdOn;

  private PostPrivacy postPrivacy;

  private String location;
}
