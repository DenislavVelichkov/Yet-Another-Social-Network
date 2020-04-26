package org.java.yasn.data.entities.gallery;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "photo_albums")
public class PhotoAlbum extends BaseEntity {

  @ManyToOne(targetEntity = PersonalGallery.class,
  fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "gallery_id", referencedColumnName = "id")
  private PersonalGallery personalGallery;

 /* @OneToMany(targetEntity = Picture.class,
      mappedBy = "album",
      cascade = CascadeType.PERSIST)
  private Collection<Picture> pictures;*/

  @Column(nullable = false)
  private String name;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "album_image")
  private String albumImg;

}
