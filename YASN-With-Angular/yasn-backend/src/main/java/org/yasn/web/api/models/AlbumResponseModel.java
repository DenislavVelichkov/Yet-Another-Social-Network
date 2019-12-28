package org.yasn.web.api.models;

import java.util.List;

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
  private List<PictureResponseModel> pictures;
  private String name;
}
