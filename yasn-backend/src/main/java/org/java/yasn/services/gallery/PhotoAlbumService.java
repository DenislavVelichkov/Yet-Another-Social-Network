package org.java.yasn.services.gallery;

import org.java.yasn.data.models.service.gallery.PhotoAlbumServiceModel;

public interface PhotoAlbumService {
  PhotoAlbumServiceModel getAlbumById(String id);
}
