package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.view.UserProfileViewModel;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class WallPostBindingModel {
  private UserProfileViewModel postOwner;
  private MultipartFile postPicture;
  private String postContent;
  private long likes;
  private String location;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
}
