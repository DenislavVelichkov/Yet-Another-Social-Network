package org.yasn.service;

import java.util.List;

// source: https://www.baeldung.com/get-user-in-spring-security
public interface AuthenticatedUserService {
  String getUsername();

  List<String> getRoles();
}
