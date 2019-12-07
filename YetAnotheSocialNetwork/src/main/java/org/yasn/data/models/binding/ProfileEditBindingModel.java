package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileEditBindingModel {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String gender;
  private String birthday;
  private String confirmEmail;
  private String confirmPassword;
  private String profilePicture;
  private String coverPicture;
}
