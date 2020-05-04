package org.java.yasn.services.wall;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.entities.LikeId;
import org.java.yasn.data.entities.gallery.PersonalGallery;
import org.java.yasn.data.entities.gallery.PhotoAlbum;
import org.java.yasn.data.entities.gallery.Picture;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.data.entities.wall.Like;
import org.java.yasn.data.entities.wall.WallPost;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.repository.LikeRepository;
import org.java.yasn.repository.gallery.PersonalGalleryRepository;
import org.java.yasn.repository.gallery.PhotoAlbumRepository;
import org.java.yasn.repository.gallery.PictureRepository;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.repository.wall.WallPostRepository;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.web.models.binding.CommentModel;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.CommentResponseModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WallServiceImpl implements WallService {
  private final WallPostRepository wallPostRepository;
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;
  private final LikeRepository likeRepository;
  private final CloudinaryService cloudinaryService;
  private final PhotoAlbumRepository photoAlbumRepository;
  private final PersonalGalleryRepository galleryRepository;
  private final PictureRepository pictureRepository;
  private final UserProfileRepository userProfileRepository;
  private String imgUrl = "";

  public WallServiceImpl(WallPostRepository wallPostRepository,
                         UserProfileService userProfileService,
                         ModelMapper modelMapper,
                         LikeRepository likeRepository,
                         CloudinaryService cloudinaryService,
                         PhotoAlbumRepository photoAlbumRepository,
                         PersonalGalleryRepository galleryRepository,
                         PictureRepository pictureRepository,
                         UserProfileRepository userProfileRepository) {
    this.wallPostRepository = wallPostRepository;
    this.userProfileService = userProfileService;
    this.modelMapper = modelMapper;
    this.likeRepository = likeRepository;
    this.cloudinaryService = cloudinaryService;
    this.photoAlbumRepository = photoAlbumRepository;
    this.galleryRepository = galleryRepository;
    this.pictureRepository = pictureRepository;
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public WallPostResponseModel createPost(WallPostModel postModel, MultipartFile[] pictures) {
    WallPostServiceModel wallPostServiceModel = new WallPostServiceModel();
    wallPostServiceModel.setPostOwner(
        this.userProfileService.findUserProfileById(postModel.getPostOwnerId()));
    wallPostServiceModel.setPostContent(postModel.getPostContent());
    wallPostServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));


    WallPost wallPost =
        this.modelMapper.map(wallPostServiceModel, WallPost.class);
    this.modelMapper.validate();

    if (wallPost.getPostPrivacy() == null) {
      wallPost.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    WallPost newPost = this.wallPostRepository.saveAndFlush(wallPost);

    addPostPictures(newPost, pictures);

    long likesCount = this.likeRepository
        .findLikesById_PostId(wallPostServiceModel.getId())
        .size();
    Collection<String> postPictureUrls = this.pictureRepository
        .findAllByWallPostId(wallPost.getId())
        .stream()
        .map(Picture::getPictureUrl)
        .collect(Collectors.toCollection(LinkedList::new));

    WallPostResponseModel response =
        new WallPostResponseModel(newPost.getId(),
            wallPostServiceModel.getPostOwner().getFullName(),
            wallPostServiceModel.getPostOwner().getProfilePicture(),
            wallPostServiceModel.getPostContent(),
            postPictureUrls,
            wallPostServiceModel.getCreatedOn(),
            likesCount);

    return response;
  }

  @Override
  public WallPostServiceModel findWallPostById(String id) {
    WallPost post = this.wallPostRepository.findById(id)
                                           .orElseThrow(
                                               () -> new UsernameNotFoundException(
                                                   ExceptionMessages.INCORRECT_ID));
    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(post, WallPostServiceModel.class);

    this.modelMapper.validate();

    return wallPostServiceModel;
  }

  @Override
  public Collection<WallPostResponseModel> displayAllPosts() {
    Collection<WallPostServiceModel> allPostsServiceModels = this.wallPostRepository
        .findAll()
        .stream()
        .map(wallPost ->
            this.modelMapper.map(wallPost, WallPostServiceModel.class))
        .collect(Collectors.toList());
    this.modelMapper.validate();

    return allPostsServiceModels.stream()
                                .map(p -> {
                                  long likesCount = this.likeRepository
                                      .findLikesById_PostId(p.getId())
                                      .size();
                                  Collection<String> postPictureUrls = this.pictureRepository
                                      .findAllByWallPostId(p.getId())
                                      .stream()
                                      .map(Picture::getPictureUrl)
                                      .collect(Collectors.toCollection(LinkedList::new));

                                  return new WallPostResponseModel(
                                      p.getId(),
                                      p.getPostOwner().getFullName(),
                                      p.getPostOwner().getProfilePicture(),
                                      p.getPostContent(),
                                      postPictureUrls,
                                      p.getCreatedOn(),
                                      likesCount);
                                })
                                .collect(Collectors.toCollection(LinkedList::new));
  }

  public void likePost(String postId, String profileId) {

    Like like = new Like();


    LikeId likeId = new LikeId();
    likeId.setPostId(postId);
    likeId.setProfileId(profileId);

    like.setId(likeId);
    like.setPostAlreadyLiked(true);


    this.likeRepository.saveAndFlush(like);
  }

  @Override
  public void unlikePost(WallPostServiceModel wallPostServiceModel, String activeUser) {

  }

  @Override
  public CommentResponseModel createComment(CommentModel comment, MultipartFile[] picture) {

    return null;
  }

  @Override
  public boolean isPostLikedByActiveUser(String postId, String profileId) {
    Optional<Like> like =
        this.likeRepository.findLikeById_PostIdAndId_ProfileId(postId, profileId);

    return like.isPresent() && like.get().isPostAlreadyLiked();
  }

  private void addPostPictures(WallPost wallPost,
                               MultipartFile[] pictures) {
    if (pictures.length != 0) {
      String albumId = createPersonalGallery(wallPost.getPostOwner().getId());
      PhotoAlbum photoAlbum = this.photoAlbumRepository.findById(albumId).get();

      Arrays.stream(pictures)
            .forEach(multipartFile -> {
              try {
                imgUrl = this.cloudinaryService.uploadImage(multipartFile);
                Picture picture = new Picture();
                picture.setWallPost(wallPost);
                picture.setAlbum(photoAlbum);
                picture.setPictureUrl(imgUrl);
                picture.setUploadedOn(new Timestamp(new Date().getTime()));

                this.pictureRepository.saveAndFlush(picture);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
    }

  }

  private String createPersonalGallery(String postOwnerId) {
    Optional<PersonalGallery> personalGallery =
        this.galleryRepository.findByGalleryOwner_Id(postOwnerId);

    if (personalGallery.isEmpty()) {
      UserProfile userProfile = this.userProfileRepository.findById(postOwnerId).get();
      PersonalGallery gallery = new PersonalGallery();
      gallery.setGalleryOwner(userProfile);
      PhotoAlbum album = new PhotoAlbum();
      album.setCreatedOn(new Timestamp(new Date().getTime()));
      album.setName("Pictures From Posts");
      album.setPersonalGallery(gallery);

      return this.photoAlbumRepository.saveAndFlush(album).getId();
    }

    return this.photoAlbumRepository.findByPersonalGalleryId(personalGallery.get().getId()).get().getId();
  }
}
