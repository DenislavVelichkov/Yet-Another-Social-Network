package org.java.yasn.services.gallery;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.data.entities.gallery.PhotoAlbum;
import org.java.yasn.data.models.service.gallery.PhotoAlbumServiceModel;
import org.java.yasn.repository.gallery.PhotoAlbumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhotoAlbumServiceImpl implements PhotoAlbumService {

  private final PhotoAlbumRepository albumRepository;
  private final ModelMapper modelMapper;

  @Override
  public PhotoAlbumServiceModel getAlbumById(String albumId) {
    PhotoAlbum album = this.albumRepository.findById(albumId)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                               String.format(ExceptionMessages.ALBUM_DOESNT_EXISTS, albumId)));

    return this.modelMapper.map(album, PhotoAlbumServiceModel.class);
  }
}
