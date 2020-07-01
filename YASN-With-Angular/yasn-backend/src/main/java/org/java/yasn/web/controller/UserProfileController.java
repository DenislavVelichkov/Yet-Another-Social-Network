package org.java.yasn.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.data.models.service.BaseServiceModel;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.services.user.UserProfileService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profile")
@AllArgsConstructor
public class UserProfileController {

  private final UserProfileService userProfileService;

  @InitBinder
  public void allowEmptyDateBinding(WebDataBinder binder) {
    // tell spring to set empty values as null instead of empty string.
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }


  @GetMapping(value = "/{profileId}", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> getUserProfileInfo(@PathVariable String profileId) {
    UserProfileServiceModel userProfile = userProfileService.findUserProfileById(profileId);
    Map<String, String> response = new HashMap<>();
    response.put("userProfileId", userProfile.getId());
    response.put("userFullName", userProfile.getFullName());
    response.put("profileUsername", userProfile.getProfileOwner().getUsername());
    response.put("avatarPictureUrl", userProfile.getProfilePicture());
    response.put("coverPictureUrl", userProfile.getCoverPicture());
    response.put("profileFriends", userProfile.getFriends()
                                              .stream()
                                              .map(BaseServiceModel::getId)
                                              .collect(Collectors.joining()));

    return ResponseEntity.ok(response);
  }

  @GetMapping("/friends/{userProfileId}")
  public ResponseEntity<?> getAllFriendsIds(
      @PathVariable(name = "userProfileId") String userProfileId) {
    Collection<String> response = this.userProfileService.getProfileFriendsIds(userProfileId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/timeline/{profileId}")
  public ResponseEntity<?> activeUserTimeline(@PathVariable String profileId) {

    return null;
  }

  @PostMapping("/timeline/post")
  public ResponseEntity<?> postOnGuestTimeline() {

    return null;
  }

  @GetMapping("/guest/{profileId}")
  public ResponseEntity<?> guestProfile() {

    return null;
  }

  @PostMapping("/guest/comment")
  public ResponseEntity<?> postCommentOnGuestTimelinePost() {

    return null;
  }

  @PostMapping("/timeline/comment")
  public ResponseEntity<?> postCommentOnTimelinePost() {

    return null;
  }

  @GetMapping("/edit/{id}")
  public ResponseEntity<?> profileEdit(@PathVariable(name = "id") String profileId) {


    return null;
  }

  @PostMapping("/edit/{id}")
  public ResponseEntity<?> finalizeEditing() {

    return null;
  }
}


