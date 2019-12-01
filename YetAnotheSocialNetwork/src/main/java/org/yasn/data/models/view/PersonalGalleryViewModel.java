package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryViewModel extends BaseViewModel {

  private String photo;
  private UserProfileViewModel photoOwner;
}
