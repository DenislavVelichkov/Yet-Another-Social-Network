package org.yasn.data.entities.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.yasn.common.enums.Authorities;
import org.yasn.data.entities.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

  @Enumerated(EnumType.STRING)
  private Authorities authority;

  @Override
  public String getAuthority() {
    return this.authority.name();
  }

}
