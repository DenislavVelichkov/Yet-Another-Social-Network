package org.java.yasn.repository.gallery;

import java.util.Optional;

import org.java.yasn.data.entities.gallery.PersonalGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalGalleryRepository extends JpaRepository<PersonalGallery, String> {
  Optional<PersonalGallery> findByGalleryOwner_Id(String id);
}
