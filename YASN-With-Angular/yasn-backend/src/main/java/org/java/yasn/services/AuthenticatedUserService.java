package org.java.yasn.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface AuthenticatedUserService {
  String getUsername();

  Collection<? extends GrantedAuthority> getRoles();
}
