package org.java.yasn.data.entities.gallery;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.yasn.data.entities.BaseEntity;
import org.java.yasn.data.entities.wall.PostComment;
import org.java.yasn.data.entities.wall.WallPost;

@Data
@Entity
@Table(name = "pictures")
@EqualsAndHashCode(callSuper = true)
public class Picture extends BaseEntity {

  @Column(name = "picture")
  private String pictureUrl;

  @ManyToOne(targetEntity = PhotoAlbum.class,
      cascade = CascadeType.PERSIST,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "photo_album_id",
      referencedColumnName = "id",
      nullable = false)
  private PhotoAlbum album;

  @ManyToOne(targetEntity = WallPost.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "post_id", referencedColumnName = "id")
  private WallPost wallPost;

  @ManyToOne(targetEntity = PostComment.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "comment_id", referencedColumnName = "id")
  private PostComment comment;

  @Column(
      name = "uploaded_on")
  private Timestamp uploadedOn;

}


