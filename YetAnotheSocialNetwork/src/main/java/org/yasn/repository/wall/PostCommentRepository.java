package org.yasn.repository.wall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.wall.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, String> {
  List<PostComment> findAllByParentPost_Id(String id);

}
