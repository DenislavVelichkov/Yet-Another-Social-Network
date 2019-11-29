package org.yasn.web.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.LikeId;
import org.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
public class LikeDetailsResponse {
  LikeId id;
  WallPost likeOwner;
  boolean isLiked;
}
