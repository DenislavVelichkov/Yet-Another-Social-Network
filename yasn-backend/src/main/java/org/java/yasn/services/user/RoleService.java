package org.java.yasn.services.user;

import java.util.Set;

import org.java.yasn.common.enums.Authorities;
import org.java.yasn.data.models.service.user.RoleServiceModel;

public interface RoleService {

  void seedRolesInDb();

  Set<RoleServiceModel> findAllRoles();

  RoleServiceModel findByAuthority(Authorities authority);
}
