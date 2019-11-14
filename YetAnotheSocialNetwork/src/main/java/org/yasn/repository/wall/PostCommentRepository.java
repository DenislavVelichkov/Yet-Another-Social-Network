package org.yasn.repository.wall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.wall.PostComment;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, String> {
  List<PostComment> findAllByParentPost_Id(String id);

}
