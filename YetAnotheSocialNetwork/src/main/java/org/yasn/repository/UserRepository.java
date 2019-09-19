package org.yasn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
