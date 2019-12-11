package org.yasn.data.models.view;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryViewModel extends BaseViewModel {
  Set<PhotoAlbumViewModel> photoAlbums;
  UserProfile galleryOwner;
}
