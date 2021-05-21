package org.java.yasn.services;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
  @Override
  public String getUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  @Override
  public Collection<? extends GrantedAuthority> getRoles() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
  }
}