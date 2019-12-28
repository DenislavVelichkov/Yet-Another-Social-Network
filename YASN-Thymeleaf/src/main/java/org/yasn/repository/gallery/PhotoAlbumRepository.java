package org.yasn.repository.gallery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.gallery.PhotoAlbum;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, String> {

  Optional<PhotoAlbum> findByNameAndPersonalGallery_GalleryOwner_Id(String albumName, String albumOwnerId);

  Optional<PhotoAlbum> findById(String id);
}
