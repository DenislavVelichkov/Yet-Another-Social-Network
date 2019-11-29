package org.yasn.data.entities.user;

import org.yasn.data.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "gallery")
public class PersonalGallery extends BaseEntity {

  @Column(name = "photo")
  String photo;

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "photo_owner_id")
  UserProfile photoOwner;
}