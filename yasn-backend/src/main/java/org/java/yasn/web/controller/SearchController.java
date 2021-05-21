package org.java.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.java.yasn.services.user.UserProfileService;
import org.java.yasn.web.models.response.SearchResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchController {

  private final UserProfileService userProfileService;

  @GetMapping(value = "/user-profile/{searchQuery}")
  public ResponseEntity<SearchResultModel> createNotificationOnPost(
      @PathVariable (name ="searchQuery") String searchQuery) {

    SearchResultModel response =
        this.userProfileService.searchForUserProfile(searchQuery);

    return ResponseEntity.ok(response);
  }
}
