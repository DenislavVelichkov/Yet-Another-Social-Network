package org.yasn.data.models.view;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAlbumViewModel {
  private Set<PictureViewModel> pictures;
  private Timestamp createdOn;
  private String albumImg;
}
