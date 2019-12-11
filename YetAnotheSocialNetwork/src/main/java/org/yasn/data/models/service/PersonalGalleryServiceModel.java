package org.yasn.data.models.service;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryServiceModel extends BaseServiceModel {
  Set<PhotoAlbumServiceModel> photoAlbums;
  UserProfile galleryOwner;
}
