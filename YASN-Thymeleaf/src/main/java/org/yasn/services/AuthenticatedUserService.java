package org.yasn.services;

import java.util.List;

public interface AuthenticatedUserService {
  String getUsername();

  List<String> getRoles();
}
