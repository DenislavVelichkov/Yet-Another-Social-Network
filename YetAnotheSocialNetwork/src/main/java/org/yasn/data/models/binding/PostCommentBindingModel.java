package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.data.models.view.WallPostViewModel;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentBindingModel {
  private WallPostViewModel parentPost;
  private String commentContent;
  private boolean isPostLiked;
  private MultipartFile commentPicture;
}
