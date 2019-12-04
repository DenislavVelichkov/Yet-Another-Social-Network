package org.yasn.repository.wall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasn.data.entities.wall.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {

  Optional<Like> findById_ProfileAndLikeOwner_Id(String profileId, String postId);
}
