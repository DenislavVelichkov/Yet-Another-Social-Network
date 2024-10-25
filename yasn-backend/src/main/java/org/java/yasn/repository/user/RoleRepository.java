package org.java.yasn.repository.user;

import org.java.yasn.common.enums.Authorities;
import org.java.yasn.data.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByAuthority(Authorities authority);
}
