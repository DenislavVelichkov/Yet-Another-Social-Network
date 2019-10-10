package org.yasn.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.domain.entities.user.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
