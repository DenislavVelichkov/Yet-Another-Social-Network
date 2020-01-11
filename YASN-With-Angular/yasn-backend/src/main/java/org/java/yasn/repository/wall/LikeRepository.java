package org.java.yasn.repository.wall;

import java.util.Optional;

import org.java.yasn.data.entities.wall.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {

  Optional<Like> findById_ProfileAndLikeOwner_Id(String profileId, String postId);
}
