package org.yasn.service;

import java.util.List;

public interface AuthenticatedUserService {
  String getUsername();

  List<String> getRoles();
}
