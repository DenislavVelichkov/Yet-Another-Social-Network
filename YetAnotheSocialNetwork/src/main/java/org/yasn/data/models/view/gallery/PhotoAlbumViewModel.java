package org.yasn.data.models.view.gallery;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.view.BaseViewModel;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAlbumViewModel extends BaseViewModel {
  private PersonalGalleryViewModel personalGallery;
  private Set<PictureViewModel> pictures;
  private String name;
  private Timestamp createdOn;
  private String albumImg;
}
