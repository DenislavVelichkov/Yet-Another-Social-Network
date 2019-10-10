package org.yasn.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.domain.entities.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByAuthority(String authority);
}
