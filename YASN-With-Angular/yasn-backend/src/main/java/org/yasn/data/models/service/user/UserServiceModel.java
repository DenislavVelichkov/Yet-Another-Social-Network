package org.yasn.data.models.service.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.BaseServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {

  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private Date birthday;
  private boolean isActive;
  private Timestamp createdOn;
  private Set<RoleServiceModel> authorities;

  @JsonIgnore
  private UserProfileServiceModel userProfile;
}
