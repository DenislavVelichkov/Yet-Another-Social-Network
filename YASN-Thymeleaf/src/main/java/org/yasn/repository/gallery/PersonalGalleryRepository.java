package org.yasn.repository.gallery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.data.entities.gallery.PersonalGallery;

@Repository
public interface PersonalGalleryRepository extends JpaRepository<PersonalGallery, String> {
  Optional<PersonalGallery> findByGalleryOwner_Id(String id);
}
