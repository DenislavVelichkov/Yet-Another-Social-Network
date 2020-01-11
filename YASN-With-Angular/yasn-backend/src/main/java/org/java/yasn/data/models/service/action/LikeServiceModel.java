package org.java.yasn.data.models.service.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.LikeId;
import org.java.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
public class LikeServiceModel {
  LikeId id;
  WallPost likeOwner;
}
