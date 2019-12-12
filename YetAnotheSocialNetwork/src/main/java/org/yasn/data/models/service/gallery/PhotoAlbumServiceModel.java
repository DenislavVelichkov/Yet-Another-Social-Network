package org.yasn.data.models.service.gallery;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.BaseServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAlbumServiceModel extends BaseServiceModel {
  private Set<PictureServiceModel> pictures;
  private Timestamp createdOn;
  private String albumImg;
}
