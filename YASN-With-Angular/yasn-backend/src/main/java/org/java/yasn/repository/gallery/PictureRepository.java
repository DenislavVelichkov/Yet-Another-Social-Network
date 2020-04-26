package org.java.yasn.repository.gallery;

import java.util.Collection;

import org.java.yasn.data.entities.gallery.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
  Collection<Picture> findAllByWallPostId(String postId);
}
