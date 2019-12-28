package org.yasn.data.entities.gallery;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

  @Column(name = "picture")
  private String pictureUrl;

  @ManyToOne(targetEntity = PhotoAlbum.class)
  @JoinColumn(
      name = "photo_album_id", referencedColumnName = "id")
  private PhotoAlbum album;

  @Column(
      name = "uploaded_on")
  private Timestamp uploadedOn;

}
