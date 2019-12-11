package org.yasn.data.entities.gallery;

import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "galleries")
public class PersonalGallery extends BaseEntity {

  @Column(name = "photo_albums")
  @OneToMany(targetEntity = PhotoAlbum.class,
      cascade = CascadeType.ALL,
      mappedBy = "personalGallery")
  Set<PhotoAlbum> photoAlbums;

  @OneToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "gallery_owner_id", referencedColumnName = "id")
  UserProfile galleryOwner;
}
