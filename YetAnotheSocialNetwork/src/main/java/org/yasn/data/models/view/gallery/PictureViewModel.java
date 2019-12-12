package org.yasn.data.models.view.gallery;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.view.BaseViewModel;

@Getter
@Setter
@NoArgsConstructor
public class PictureViewModel extends BaseViewModel {
  private String pictureUrl;
  private PhotoAlbumViewModel album;
  private Timestamp uploadedOn;
}
