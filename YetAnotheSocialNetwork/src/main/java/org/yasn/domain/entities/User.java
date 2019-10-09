package org.yasn.domain.entities;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private Date birthday;
  private boolean isActive;
  private Timestamp createdOn;
  private Set<Role> authorities;

  public User() {
  }

  @Override
  @Column(name = "username", nullable = false, unique = true, updatable = false)
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "first_name", nullable = false, updatable = false)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name", nullable = false, updatable = false)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  @Column(name = "password", nullable = false)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "email", nullable = false, unique = true)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "is_active", nullable = false)
  public boolean isActive() {
    return this.isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on", updatable = false)
  public Timestamp getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(
          name = "user_id",
          referencedColumnName = "id"
      ),
      inverseJoinColumns = @JoinColumn(
          name = "role_id",
          referencedColumnName = "id"
      )
  )
  @Override
  public Set<Role> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Set<Role> authorities) {
    this.authorities = authorities;
  }

  @Column(name = "gender", nullable = false, updatable = false)
  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Column(name = "birthday", nullable = false, updatable = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Override
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @Transient
  public boolean isEnabled() {
    return true;
  }
}

