package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentServiceModel extends BaseServiceModel {
  private WallPostServiceModel parentPost;
  private String commentContent;
  private boolean isPostLiked;
  private byte[] commentPicture;
}
