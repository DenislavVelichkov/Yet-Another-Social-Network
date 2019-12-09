package org.yasn.data.entities.user;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gallery")
public class PersonalGallery extends BaseEntity {

  @Column(name = "photo")
  String photo;

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "photo_owner_id")
  UserProfile photoOwner;
}
