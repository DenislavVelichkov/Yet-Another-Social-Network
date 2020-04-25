package org.java.yasn.repository;

import java.util.Collection;
import java.util.Optional;

import org.java.yasn.data.entities.wall.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {

  Optional<Like> findLikeById_PostIdAndId_ProfileId(String postId, String profileId);

  Collection<Like> findLikesById_PostId(String postId);
}
