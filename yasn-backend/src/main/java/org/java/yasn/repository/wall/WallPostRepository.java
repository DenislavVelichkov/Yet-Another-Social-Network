package org.java.yasn.repository.wall;

import java.util.Collection;
import java.util.Optional;

import org.java.yasn.data.entities.wall.WallPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallPostRepository extends JpaRepository<WallPost, String> {

  Optional<WallPost> findById(String id);

  Collection<WallPost> findAllByPostOwner_ProfileOwner_Username(String string);

  Collection<WallPost> findAllByPostOwner_Id(String id);
}
