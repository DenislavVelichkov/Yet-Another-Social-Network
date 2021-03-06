package org.java.yasn.data.entities.wall;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.LikeId;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "likes")
public class Like {

  @EmbeddedId
  LikeId id;

  boolean isPostAlreadyLiked;
}


