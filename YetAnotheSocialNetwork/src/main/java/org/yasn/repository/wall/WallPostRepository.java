package org.yasn.repository.wall;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.wall.WallPost;

@Repository
public interface WallPostRepository extends JpaRepository<WallPost, String> {

  Optional<WallPost> findById(String id);

  List<WallPost> findAllByPostOwner_ProfileOwner_Username(String string);

  List<WallPost> findAllByPostOwner_Id(String id);
}
