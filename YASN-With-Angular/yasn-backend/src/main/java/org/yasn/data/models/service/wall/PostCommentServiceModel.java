package org.yasn.data.models.service.wall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/models/binding/PostCommentBindingModel.java
import org.springframework.web.multipart.MultipartFile;
=======
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/models/service/wall/PostCommentServiceModel.java

@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/models/binding/PostCommentBindingModel.java
@AllArgsConstructor
public class PostCommentBindingModel {

  private String commentContent;
  private MultipartFile commentPicture;

=======
public class PostCommentServiceModel extends BaseServiceModel {

  private WallPostServiceModel parentPost;
  private UserProfileServiceModel commentOwner;
  private String commentContent;
  private String commentPicture;
  private Timestamp createdOn;
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/models/service/wall/PostCommentServiceModel.java
}
