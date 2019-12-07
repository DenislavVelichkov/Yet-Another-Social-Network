package org.yasn.service.interfaces;

import java.util.Set;

import org.yasn.data.models.service.RoleServiceModel;

public interface RoleService {

  void seedRolesInDb();

  Set<RoleServiceModel> findAllRoles();

  RoleServiceModel findByAuthority(String authority);
}
