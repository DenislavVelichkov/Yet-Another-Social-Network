package org.yasn.web.api;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.management.OperationsException;

import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.ExceptionMessages;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.services.CloudinaryService;
import org.yasn.services.action.NotificationService;
import org.yasn.services.gallery.PersonalGalleryService;
import org.yasn.services.user.UserProfileService;
import org.yasn.services.wall.WallService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActionApiController {

  private final WallService wallService;
  private final NotificationService notificationService;
  private final UserProfileService profileService;
  private final PersonalGalleryService galleryService;
  private final CloudinaryService cloudinaryService;


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
  public ResponseEntity<?> createAlbum(
      @RequestParam(name = "profileId") String profileId,
      @RequestParam(name = "albumName") String albumName,
      @RequestParam(name = "photos") MultipartFile[] photos) {

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

    Map<String, Object> restResponse = new LinkedHashMap<>();
    restResponse.put("error", "");
    restResponse.put("errorkeys", "");
    restResponse.put("initial preview configuration", "");
    restResponse.put("initialPreviewThumbTags", "");
    restResponse.put("append", false);

    var response =
        JSONObject.toJSONString(restResponse);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}

