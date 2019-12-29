package org.yasn.web.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
