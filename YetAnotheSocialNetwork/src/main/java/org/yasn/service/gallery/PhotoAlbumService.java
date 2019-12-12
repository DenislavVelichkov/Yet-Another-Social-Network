package org.yasn.service.gallery;

import org.yasn.data.models.service.gallery.PhotoAlbumServiceModel;

public interface PhotoAlbumService {
  PhotoAlbumServiceModel getAlbumById(String id);
}
