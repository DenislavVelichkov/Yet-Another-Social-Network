package org.java.yasn.web.models.binding;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.models.service.user.RoleServiceModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsModel {

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

  private String userProfileId;
}
