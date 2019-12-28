package org.yasn.data.models.view.gallery;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.models.view.BaseViewModel;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryViewModel extends BaseViewModel {
  Set<PhotoAlbumViewModel> photoAlbums;
  UserProfile galleryOwner;
}
