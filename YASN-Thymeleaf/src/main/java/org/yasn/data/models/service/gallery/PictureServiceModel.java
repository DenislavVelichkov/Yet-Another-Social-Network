package org.yasn.data.models.service.gallery;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.BaseServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class PictureServiceModel extends BaseServiceModel {
  private String pictureUrl;
  private PhotoAlbumServiceModel album;
  private Timestamp uploadedOn;
}
