package org.yasn.data.entities.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.yasn.data.entities.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

  @Column(
      name = "username",
      nullable = false,
      unique = true)
  private String username;

  @Column(
      name = "first_name",
      nullable = false)
  private String firstName;

  @Column(
      name = "last_name",
      nullable = false)
  private String lastName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(
      name = "email",
      nullable = false,
      unique = true)
  private String email;

  @Column(
      name = "gender",
      nullable = false)
  private String gender;

  @Column(name = "birthday", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthday;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(
      name = "created_on",
      updatable = false,
      nullable = false)
  private Timestamp createdOn;

  @OneToOne(
      targetEntity = UserProfile.class,
      mappedBy = "profileOwner",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private UserProfile userProfile;

  @ManyToMany(targetEntity = Role.class,
      fetch = FetchType.EAGER)
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
  private Set<Role> authorities;

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

