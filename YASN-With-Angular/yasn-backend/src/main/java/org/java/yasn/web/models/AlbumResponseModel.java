package org.java.yasn.web.models;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseModel {
  private String id;
  private Collection<PictureResponseModel> pictures;
  private String name;
}
