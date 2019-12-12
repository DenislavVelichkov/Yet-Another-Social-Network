package org.yasn.web.api.models;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.view.gallery.PictureViewModel;

@Getter
@Setter
@NoArgsConstructor
public class AlbumResponseModel {
  private String id;
  private Set<PictureViewModel> pictures;
  private String name;
}
