package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterBindingModel {

  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String confirmPassword;
  private String email;
  private String confirmEmail;
  private String gender;
  private String birthday;
}
