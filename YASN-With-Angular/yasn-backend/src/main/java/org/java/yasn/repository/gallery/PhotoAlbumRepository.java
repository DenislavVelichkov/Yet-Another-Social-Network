package org.java.yasn.repository.gallery;

import java.util.Optional;

import org.java.yasn.data.entities.gallery.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, String> {

  Optional<PhotoAlbum> findByNameAndPersonalGallery_GalleryOwner_Id(String albumName, String albumOwnerId);

  Optional<PhotoAlbum> findById(String id);

  Optional<PhotoAlbum> findByPersonalGalleryId(String galleryId);
}
