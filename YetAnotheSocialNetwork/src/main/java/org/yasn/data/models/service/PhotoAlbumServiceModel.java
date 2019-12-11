package org.yasn.data.models.service;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAlbumServiceModel extends BaseServiceModel {
  private Set<PictureServiceModel> pictures;
  private Timestamp createdOn;
}
