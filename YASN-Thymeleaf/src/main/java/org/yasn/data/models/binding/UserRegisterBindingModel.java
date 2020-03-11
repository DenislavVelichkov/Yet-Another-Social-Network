package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterBindingModel {

  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private String birthday;
  private String confirmEmail;
  private String confirmPassword;
}
