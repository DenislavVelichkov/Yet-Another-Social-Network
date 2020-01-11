package org.java.yasn.data.models.service.gallery;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.data.models.service.BaseServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryServiceModel extends BaseServiceModel {
  Set<PhotoAlbumServiceModel> photoAlbums;
  UserProfile galleryOwner;
}
