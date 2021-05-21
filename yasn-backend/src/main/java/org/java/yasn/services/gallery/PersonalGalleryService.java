package org.java.yasn.services.gallery;

import java.util.Collection;
import java.util.Set;

import org.java.yasn.data.models.service.gallery.PersonalGalleryServiceModel;
import org.java.yasn.data.models.service.gallery.PhotoAlbumServiceModel;

public interface PersonalGalleryService {

  boolean uploadImages(Set<String> images, String albumOwnerId, String albumName);

  PersonalGalleryServiceModel findByOwnerId(String id);

  Collection<PhotoAlbumServiceModel> getAlbumsByProfileId(String profileId);
}
