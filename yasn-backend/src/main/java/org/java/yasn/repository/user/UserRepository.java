package org.java.yasn.repository.user;

import java.util.Optional;

import org.java.yasn.data.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
}
