package org.yasn.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

  private String username;
  private String password;
  private String email;
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

