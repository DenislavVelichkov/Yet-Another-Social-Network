package org.java.yasn.web.controller;

import java.io.IOException;

import lombok.AllArgsConstructor;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.wall.PostCommentService;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.validation.wall.CommentValidator;
import org.java.yasn.validation.wall.PostValidator;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.WallPostResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/home/news-feed")
@AllArgsConstructor
public class WallController {

  private final WallService wallService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;
  private final PostValidator postValidator;
  private final CommentValidator commentValidator;

  @PostMapping("/post")
  public ResponseEntity<?> postOnNewsFeed(
      @RequestPart(name = "post") WallPostModel post) throws IOException {

    WallPostResponseModel response = this.wallService.createPost(post);

    return ResponseEntity.ok(response);
  }


  @PostMapping("/post/comment")
  public ResponseEntity<?> postCommentOnPost() {


    return null;
  }

  private WallPostServiceModel setDefaultModelAttributes(WallPostServiceModel wallPostServiceModel,
                                                         WallPostModel post) throws IOException {
    if (!wallPostServiceModel.getPostPicture().isEmpty()) {
      wallPostServiceModel.setPostPicture(
          this.cloudinaryService.uploadImage(post.getPostPicture()));
    } else {
      wallPostServiceModel.setPostPicture(null);
    }

    if (wallPostServiceModel.getPostPrivacy() == null) {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }
    return wallPostServiceModel;
  }
}
