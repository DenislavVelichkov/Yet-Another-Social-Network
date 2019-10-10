package org.yasn.repository.wall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.domain.entities.wall.WallPost;

@Repository
public interface WallPostRepository extends JpaRepository<WallPost, String> {
}
