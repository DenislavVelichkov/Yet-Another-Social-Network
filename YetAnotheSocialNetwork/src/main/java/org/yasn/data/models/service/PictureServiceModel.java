package org.yasn.data.models.service;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureServiceModel extends BaseServiceModel {
  private String pictureUrl;
  private PhotoAlbumServiceModel album;
  private Timestamp uploadedOn;
}
