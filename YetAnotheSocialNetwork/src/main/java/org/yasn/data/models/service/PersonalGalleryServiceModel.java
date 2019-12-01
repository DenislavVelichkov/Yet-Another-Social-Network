package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalGalleryServiceModel {
  String photo;
  UserProfileServiceModel photoOwner;

}
