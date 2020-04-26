package org.java.yasn.web.controller;

import java.io.IOException;

import lombok.AllArgsConstructor;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/home/news-feed")
@AllArgsConstructor
public class WallController {

  private final WallService wallService;


  @PostMapping("/post")
  public ResponseEntity<?> postOnNewsFeed(
      @RequestPart(name = "post") WallPostModel post,
      @RequestPart(name = "postPicture") MultipartFile[] picture) throws IOException {

    WallPostResponseModel response = this.wallService.createPost(post, picture);

    return ResponseEntity.ok(response);
  }


  @PostMapping("/post/comment")
  public ResponseEntity<?> postCommentOnPost() {


    return null;
  }

}
