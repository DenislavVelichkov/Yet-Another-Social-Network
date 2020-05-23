package org.java.yasn.web.controller;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/home")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class HomeController {
  private final WallService wallService;

  @GetMapping(value = "/all-news/{currentUser}")
  public ResponseEntity<Collection<WallPostResponseModel>> allNewsFeed(@PathVariable(name = "currentUser") String currentUser) {

    Collection<WallPostResponseModel> posts = this.wallService.displayAllPosts(currentUser);

    return ResponseEntity.ok(posts);
  }

}
