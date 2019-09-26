package org.yasn.domain.models.service;

import java.util.Date;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private Date birthday;
  private Set<RoleServiceModel> authorities;

  public UserServiceModel() {
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Set<RoleServiceModel> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Set<RoleServiceModel> authorities) {
    this.authorities = authorities;
  }
}
