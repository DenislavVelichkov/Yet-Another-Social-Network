package org.yasn.data.models.service.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.LikeId;
import org.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
public class LikeServiceModel {
  LikeId id;
  WallPost likeOwner;
}
