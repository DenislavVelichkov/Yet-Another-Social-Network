package org.yasn.data.models.binding;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditBindingModel {

  private String username;
  private String oldPassword;
  private String password;
  private String confirmPassword;
  private String email;
}
