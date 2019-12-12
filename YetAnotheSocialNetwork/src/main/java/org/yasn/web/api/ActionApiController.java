package org.yasn.web.api;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.management.OperationsException;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.ExceptionMessages;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.service.CloudinaryService;
import org.yasn.service.action.NotificationService;
import org.yasn.service.gallery.PersonalGalleryService;
import org.yasn.service.gallery.PhotoAlbumService;
import org.yasn.service.user.UserProfileService;
import org.yasn.service.wall.WallService;
import org.yasn.web.api.models.AlbumResponseModel;
import org.yasn.web.controller.BaseController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActionApiController extends BaseController {

  private final WallService wallService;
  private final NotificationService notificationService;
  private final UserProfileService profileService;
  private final PersonalGalleryService galleryService;
  private final PhotoAlbumService albumService;
  private final CloudinaryService cloudinaryService;
  private final ModelMapper modelMapper;

  @PostMapping("/likes")
  public void likeAction(@ModelAttribute(name = "likePostId") String postId,
                         Principal activeUser) {

    WallPostServiceModel wallPostServiceModel =
        this.wallService.findWallPostById(postId);

    if (this.wallService.isPostLikedByActiveUser(
        activeUser.getName(), wallPostServiceModel.getId())) {
      this.wallService.unlikePost(wallPostServiceModel, activeUser.getName());
    } else {
      this.wallService.likePost(wallPostServiceModel, activeUser.getName());
    }

  }

  @PostMapping("/add-friend")
  public void sendFriendRequest(@ModelAttribute(name = "profileId") String recipientId,
                                Principal sender) throws OperationsException {

    String senderId =
        this.profileService.findUserProfileByUsername(sender.getName()).getId();

    if (recipientId.equals(senderId)) {
      throw new OperationsException(ExceptionMessages.FRIEND_REQUEST_ON_YOURSELF);
    }

    if (this.notificationService
        .findByRecipientIdSenderIdAndNotificationType(
            recipientId, senderId, NotificationType.FRIEND_REQ) == null) {

      this.notificationService.createNotification(
          recipientId, sender.getName(), NotificationType.FRIEND_REQ);

    } else {
      throw new OperationsException(ExceptionMessages.FRIEND_REQUEST_ALREADY_EXISTS);
    }

  }

  @PostMapping("/accept-friend")
  public void acceptFriendRequest(@ModelAttribute(name = "senderId") String senderId,
                                  Principal activeUser) {

    boolean successfulFriendship =
        this.profileService.addFriend(senderId, activeUser.getName());

    if (successfulFriendship) {
      this.notificationService.removeNotification(
          senderId, activeUser.getName(), NotificationType.FRIEND_REQ);
    }

  }

  @PostMapping("/create-album")
  public void createAlbum(
      @RequestParam(name = "profileId") String profileId,
      @RequestParam(name = "albumName") String albumName,
      @RequestParam(name = "photos") MultipartFile[] photos,
      HttpServletResponse response) throws IOException {

    Set<String> images =
        Arrays.stream(photos)
              .map(img -> {
                String convertedImage = null;

                try {
                  convertedImage =
                      this.cloudinaryService.uploadImage(img);
                } catch (IOException e) {
                  e.printStackTrace();
                }

                return convertedImage;
              })
              .collect(Collectors.toSet());

    this.galleryService.uploadImages(images, profileId, albumName);

    response.sendRedirect("/profile/timeline/" + profileId);
  }

  @PostMapping("/photo-album/{albumId}")
  public ResponseEntity<AlbumResponseModel> getPhotoAlbum(@PathVariable String albumId) {
    AlbumResponseModel album =
        this.modelMapper.map(
            this.albumService.getAlbumById(albumId), AlbumResponseModel.class);
    this.modelMapper.validate();

    return new ResponseEntity<>(album, HttpStatus.FOUND);
  }
}

