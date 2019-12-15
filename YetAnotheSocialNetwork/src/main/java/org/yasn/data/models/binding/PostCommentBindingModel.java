package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentBindingModel {

  private String commentContent;
  private MultipartFile commentPicture;

}
