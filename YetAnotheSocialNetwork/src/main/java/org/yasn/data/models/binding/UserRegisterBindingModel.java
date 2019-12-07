package org.yasn.data.models.binding;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.RoleServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterBindingModel {

  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private String birthday;
  private String confirmEmail;
  private String confirmPassword;
  private boolean isActive;
  private Timestamp createdOn;
  private UserProfileServiceModel userProfile;
  private Set<RoleServiceModel> authorities;
}
