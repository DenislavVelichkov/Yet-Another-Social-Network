package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentServiceModel extends BaseServiceModel {
  private WallPost parentPost;
  private String commentContent;
  private boolean isPostLiked;
  private byte[] commentPicture;
}
