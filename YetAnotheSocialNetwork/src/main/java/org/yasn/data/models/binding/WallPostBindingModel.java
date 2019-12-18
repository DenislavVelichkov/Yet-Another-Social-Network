package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.enums.PostPrivacy;

@Getter
@Setter
@NoArgsConstructor
public class WallPostBindingModel {

  private MultipartFile postPicture;
  private String postContent;
  private PostPrivacy postPrivacy;
  private String location;

}
