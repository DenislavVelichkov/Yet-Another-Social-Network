package org.yasn.repository.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.gallery.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {

}
