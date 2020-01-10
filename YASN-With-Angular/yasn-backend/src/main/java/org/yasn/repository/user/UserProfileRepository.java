package org.yasn.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.user.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

  Optional<UserProfile> findByProfileOwner_Username(String username);

  Optional<UserProfile> findById(String id);

  Optional<UserProfile> findUserProfileByProfileOwner_Email(String email);
}
