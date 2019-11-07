package org.yasn.service.interfaces;

import org.yasn.data.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

  void seedRolesInDb();

  Set<RoleServiceModel> findAllRoles();

  RoleServiceModel findByAuthority(String authority);
}
