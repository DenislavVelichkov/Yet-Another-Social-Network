package org.java.yasn.services.gallery;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.data.entities.gallery.PersonalGallery;
import org.java.yasn.data.entities.gallery.PhotoAlbum;
import org.java.yasn.data.entities.gallery.Picture;
import org.java.yasn.data.models.service.gallery.PersonalGalleryServiceModel;
import org.java.yasn.repository.gallery.PersonalGalleryRepository;
import org.java.yasn.repository.gallery.PhotoAlbumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonalGalleryServiceImpl implements PersonalGalleryService {

  private final PhotoAlbumRepository albumRepository;
  private final PersonalGalleryRepository galleryRepository;
  private final ModelMapper modelMapper;


  @Override
  public boolean uploadImages(Set<String> images,
                              String albumOwnerId,
                              String albumName) {
    PersonalGallery gallery =
        this.galleryRepository
            .findByGalleryOwner_Id(albumOwnerId)
            .orElse(null);

    if (gallery == null) {
      gallery = new PersonalGallery();
      this.galleryRepository.saveAndFlush(gallery);
    }

    PhotoAlbum album =
        this.albumRepository
            .findByNameAndPersonalGallery_GalleryOwner_Id(albumName, albumOwnerId)
            .orElse(null);
    if (albumName.isEmpty()) {
      int albumsCount = gallery.getPhotoAlbums().size() + 1;
      albumName = "Album" + "(" + albumsCount + ")";
    }

    if (album == null) {
      album = new PhotoAlbum();
    }
    PhotoAlbum finalAlbum = album;
    Set<Picture> pictures = images.stream()
                                  .map(img -> new Picture() {{
                                    setAlbum(finalAlbum);
                                    setPictureUrl(img);
                                    setUploadedOn(new Timestamp(new Date().getTime()));
                                  }})
                                  .collect(Collectors.toSet());

    try {
      album.setPictures(pictures);
      album.setAlbumImg(pictures.iterator().next().getPictureUrl());
      album.setCreatedOn(new Timestamp(new Date().getTime()));
      album.setName(albumName);
      album.setPersonalGallery(gallery);
      gallery.getPhotoAlbums().add(album);
      this.albumRepository.saveAndFlush(album);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public PersonalGalleryServiceModel findByOwnerId(String id) {
    PersonalGallery gallery = this.galleryRepository
        .findByGalleryOwner_Id(id)
        .orElse(null);

    if (gallery == null) {
      throw new IllegalArgumentException(
          String.format(ExceptionMessages.GALLERY_DOESNT_EXISTS, id));
    }

    PersonalGalleryServiceModel galleryService =
        this.modelMapper.map(gallery, PersonalGalleryServiceModel.class);
    this.modelMapper.validate();

    return galleryService;
  }
}
