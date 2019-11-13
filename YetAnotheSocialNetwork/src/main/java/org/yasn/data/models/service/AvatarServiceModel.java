package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.User;

@Getter
@Setter
@NoArgsConstructor
public class AvatarServiceModel extends BaseServiceModel {

  private String fullName;
  private String gender;
  private byte[] profilePicture;
  private User avatarOwner;
}
