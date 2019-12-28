package org.yasn.services.gallery;

import java.util.Set;

import org.yasn.data.models.service.gallery.PersonalGalleryServiceModel;

public interface PersonalGalleryService {
  boolean uploadImages(Set<String> images, String albumOwnerId, String albumName);

  PersonalGalleryServiceModel findByOwnerId(String id);
}
