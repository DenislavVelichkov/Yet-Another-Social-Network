package org.yasn.service.user;

import java.util.Set;

import org.yasn.common.enums.Authorities;
import org.yasn.data.models.service.user.RoleServiceModel;

public interface RoleService {

  void seedRolesInDb();

  Set<RoleServiceModel> findAllRoles();

  RoleServiceModel findByAuthority(Authorities authority);
}
