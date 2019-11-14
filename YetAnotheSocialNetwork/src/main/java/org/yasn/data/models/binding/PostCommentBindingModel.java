package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentBindingModel {
  private WallPost parentPost;
  private String commentContent;
  private boolean isPostLiked;
  private MultipartFile commentPicture;
}
