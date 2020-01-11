package org.java.yasn.repository.wall;

import java.util.List;

import org.java.yasn.data.entities.wall.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, String> {
  List<PostComment> findAllByParentPost_Id(String id);

}
