package org.java.yasn.web.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.PostPrivacy;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WallPostBindingModel {

  private MultipartFile postPicture;
  private String postContent;
  private PostPrivacy postPrivacy;
  private String location;

}
