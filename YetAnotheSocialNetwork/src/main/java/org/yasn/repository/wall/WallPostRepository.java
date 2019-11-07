package org.yasn.repository.wall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.wall.WallPost;

@Repository
public interface WallPostRepository extends JpaRepository<WallPost, String> {
}
