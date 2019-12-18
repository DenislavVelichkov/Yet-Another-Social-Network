package org.yasn.services.gallery;

import org.yasn.data.models.service.gallery.PhotoAlbumServiceModel;

public interface PhotoAlbumService {
  PhotoAlbumServiceModel getAlbumById(String id);
}
