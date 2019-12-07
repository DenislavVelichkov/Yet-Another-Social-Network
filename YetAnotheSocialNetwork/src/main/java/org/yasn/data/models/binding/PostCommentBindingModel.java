package org.yasn.data.models.binding;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentBindingModel {

  private String id;
  private String commentContent;
  private MultipartFile commentPicture;
  private WallPostServiceModel parentPost;
  private UserProfileServiceModel commentOwner;
  private Timestamp createdOn;
}
