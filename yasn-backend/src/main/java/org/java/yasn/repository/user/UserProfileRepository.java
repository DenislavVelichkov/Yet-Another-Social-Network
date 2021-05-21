package org.java.yasn.repository.user;

import java.util.Collection;
import java.util.Optional;

import org.java.yasn.data.entities.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

  Optional<UserProfile> findByProfileOwner_Username(String username);

  Optional<UserProfile> findById(String id);

  Optional<UserProfile> findUserProfileByProfileOwner_Email(String email);

  Collection<UserProfile> findUserProfilesByFullNameContaining(String queryParams);

}
