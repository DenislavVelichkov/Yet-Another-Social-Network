package org.yasn.data.entities.wall;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.LikeId;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "likes")
public class Like {

  @EmbeddedId
  LikeId id;

  @ManyToOne(targetEntity = WallPost.class)
  WallPost likeOwner;
}


