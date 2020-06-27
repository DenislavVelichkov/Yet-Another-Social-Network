package org.java.yasn.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.action.NotificationService;
import org.java.yasn.services.gallery.PersonalGalleryService;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.binding.LikeAPostModel;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/actions/")
@AllArgsConstructor
public class ActionApiController {

  private final WallService wallService;
  private final NotificationService notificationService;
  private final UserProfileService profileService;
  private final PersonalGalleryService galleryService;
  private final CloudinaryService cloudinaryService;
  private final SimpMessageSendingOperations sendAction;

  @PostMapping(value = "/likes/likeAPost", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public void likePostAction(@RequestBody LikeAPostModel likeAPostModel) {
    wallService.likePost(likeAPostModel);
    sendAction.convertAndSend("/like-post", likeAPostModel);
  }

  @PostMapping(value = "/likes/unLikeAPost", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public void unLikePostAction(@RequestBody LikeAPostModel likeAPostModel) {
    wallService.unlikePost(likeAPostModel);
    sendAction.convertAndSend("/unlike-post", likeAPostModel);
  }

  @PutMapping("/add-friend")
  public ResponseEntity<Map<String, String>> acceptFriendRequest(@RequestBody ActionModel actionModel) {

    Map<String, String> response =
        this.profileService.addFriend(actionModel);

    return ResponseEntity.ok(response);
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

    String response =
        JSONObject.toJSONString(restResponse);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping(value = "/check-friendship/{viewerProfileId}/{selectedProfileId}")
  public ResponseEntity<Boolean> areTheFriends(@PathVariable(name = "viewerProfileId") String viewerId,
                                               @PathVariable(name = "selectedProfileId") String selectedProfileId) {
    boolean response = this.profileService.checkFriendship(viewerId, selectedProfileId);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/check-for-pending-request/{viewerProfileId}/{selectedProfileId}/{notificationType}")
  public ResponseEntity<Boolean> areTheFriends(@PathVariable(name = "viewerProfileId") String viewerId,
                                               @PathVariable(name = "selectedProfileId") String selectedProfileId,
                                               @PathVariable(name = "notificationType") String notificationType) {
    boolean response = this.notificationService.checkForPendingRequest(viewerId, selectedProfileId, notificationType);

    return ResponseEntity.ok(response);
  }
}

