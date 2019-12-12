package org.yasn.data.models.view.gallery;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureViewModel {
  private String pictureUrl;
  private PhotoAlbumViewModel album;
  private Timestamp uploadedOn;
}
