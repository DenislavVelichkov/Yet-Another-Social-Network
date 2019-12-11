package org.yasn.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.common.enums.Authorities;
import org.yasn.data.entities.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByAuthority(Authorities authority);
}
