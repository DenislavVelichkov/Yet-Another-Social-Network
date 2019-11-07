package org.yasn.data.models.binding;

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

  public UserRegisterBindingModel() {
  }

  public String getUsername() {
    return username;
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
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getConfirmEmail() {
    return this.confirmEmail;
  }

  public void setConfirmEmail(String confirmEmail) {
    this.confirmEmail = confirmEmail;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthday() {
    return this.birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
}
