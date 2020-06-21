package org.java.yasn.web.controller;

import java.io.IOException;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.web.models.binding.CommentModel;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.CommentResponseModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/home/news-feed")
@AllArgsConstructor
public class WallController {

  private final WallService wallService;
  private final SimpMessageSendingOperations sendAction;


  @PostMapping(value = "/post", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> postOnNewsFeed(
      @RequestPart(name = "post") WallPostModel post,
      @RequestPart(name = "postPicture", required = false) MultipartFile[] pictures) throws IOException {

    WallPostResponseModel response = this.wallService.createPost(post, pictures);
    sendAction.convertAndSend("/new-post-created", response);

    return ResponseEntity.ok(response);
  }


  @PostMapping(value = "/post/comment", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> postCommentOnPost(
      @RequestPart(name = "comment") CommentModel comment,
      @RequestPart(name = "commentPicture", required = false) MultipartFile[] pictures) {

    CommentResponseModel response = this.wallService.createComment(comment, pictures);
    sendAction.convertAndSend("/comment-on-post", response);

    return ResponseEntity.ok(response);
  }

}
