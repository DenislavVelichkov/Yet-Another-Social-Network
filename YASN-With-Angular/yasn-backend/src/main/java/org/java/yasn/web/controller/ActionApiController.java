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
import org.java.yasn.web.models.binding.LikeAPostModel;
import org.java.yasn.web.models.binding.NotificationModel;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping(value = "/likes/likeAPost", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public void likePostAction(@RequestBody LikeAPostModel likeAPostModel) {

    wallService.likePost(likeAPostModel);
  }

  @PostMapping(value = "/likes/unLikeAPost", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public void unLikePostAction(@RequestBody LikeAPostModel likeAPostModel) {

    wallService.unlikePost(likeAPostModel);
  }

  @PostMapping("/add-friend")
  public ResponseEntity<?> acceptFriendRequest(@RequestBody NotificationModel notificationModel) {

    boolean successfulFriendship =
        this.profileService.addFriend(notificationModel);

    return ResponseEntity.ok(successfulFriendship);
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
}

