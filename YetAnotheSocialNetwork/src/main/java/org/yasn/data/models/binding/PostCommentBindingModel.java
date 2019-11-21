package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.data.models.view.UserProfileViewModel;
import org.yasn.data.models.view.WallPostViewModel;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentBindingModel {
  private WallPostViewModel parentPost;
  private UserProfileViewModel commentOwner;
  private Timestamp createdOn;
  private String commentContent;
  private boolean isPostLiked;
  private MultipartFile commentPicture;
}
