package org.yasn.domain.entities.user;

import org.springframework.security.core.GrantedAuthority;
import org.yasn.domain.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

  private String authority;

  public Role() {
  }

  public Role(String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
