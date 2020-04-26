package org.java.yasn.data.entities.gallery;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.BaseEntity;
import org.java.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

  @Column(name = "picture")
  private String pictureUrl;

  @ManyToOne(targetEntity = PhotoAlbum.class,
      cascade = CascadeType.PERSIST,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "photo_album_id", referencedColumnName = "id", nullable = false)
  private PhotoAlbum album;

  @ManyToOne(targetEntity = WallPost.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "wall_post_id", referencedColumnName = "id")
  private WallPost wallPost;

  @Column(
      name = "uploaded_on")
  private Timestamp uploadedOn;

}
