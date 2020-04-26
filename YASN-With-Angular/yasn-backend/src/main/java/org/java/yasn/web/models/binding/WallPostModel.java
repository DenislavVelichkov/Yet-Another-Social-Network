package org.java.yasn.web.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.PostPrivacy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WallPostModel {

  private String postOwnerId;
  private String postContent;
  private PostPrivacy postPrivacy;
  private String location;

}
