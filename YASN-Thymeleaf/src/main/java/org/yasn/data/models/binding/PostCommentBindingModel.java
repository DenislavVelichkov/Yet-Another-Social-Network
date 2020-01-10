<<<<<<< HEAD
package org.yasn.data.models.service.wall;
=======
package org.yasn.data.models.binding;
>>>>>>> experimental

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/models/binding/PostCommentBindingModel.java
import org.springframework.web.multipart.MultipartFile;
=======
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/models/service/wall/PostCommentServiceModel.java
=======
import org.springframework.web.multipart.MultipartFile;
>>>>>>> experimental

@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/models/binding/PostCommentBindingModel.java
=======
>>>>>>> experimental
@AllArgsConstructor
public class PostCommentBindingModel {

  private String commentContent;
  private MultipartFile commentPicture;

<<<<<<< HEAD
=======
public class PostCommentServiceModel extends BaseServiceModel {

  private WallPostServiceModel parentPost;
  private UserProfileServiceModel commentOwner;
  private String commentContent;
  private String commentPicture;
  private Timestamp createdOn;
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/models/service/wall/PostCommentServiceModel.java
=======
>>>>>>> experimental
}
