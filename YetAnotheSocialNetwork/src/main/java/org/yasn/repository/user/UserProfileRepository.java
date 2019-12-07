package org.yasn.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.user.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

  Optional<UserProfile> findByProfileOwner_Username(String username);

  Optional<UserProfile> findById(String id);

  Optional<UserProfile> findUserProfileByProfileOwner_Email(String email);
}
