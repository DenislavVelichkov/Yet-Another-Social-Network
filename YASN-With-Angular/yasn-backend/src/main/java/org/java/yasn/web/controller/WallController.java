package org.java.yasn.web.controller;

import java.io.IOException;
import java.security.Principal;

import lombok.AllArgsConstructor;
import org.java.yasn.common.enums.PostPrivacy;
import org.java.yasn.data.models.service.wall.PostCommentServiceModel;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.services.CloudinaryService;
import org.java.yasn.services.wall.PostCommentService;
import org.java.yasn.services.wall.WallService;
import org.java.yasn.validation.wall.CommentValidator;
import org.java.yasn.validation.wall.PostValidator;
import org.java.yasn.web.models.binding.PostCommentBindingModel;
import org.java.yasn.web.models.binding.WallPostBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/home/news-feed")
@AllArgsConstructor
public class WallController extends BaseController {

  private final WallService wallService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;
  private final PostValidator postValidator;
  private final CommentValidator commentValidator;

  @PostMapping("/post")
  public ResponseEntity<?> postOnNewsFeed(
      @RequestPart(name = "post") WallPostBindingModel post,
      @PathVariable(name = "profileOwnerId") String profileOwnerId) throws IOException {

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(post, WallPostServiceModel.class);
    this.modelMapper.validate();

    this.wallService.createPost(
        this.setDefaultModelAttributes(wallPostServiceModel, post), profileOwnerId);

    return ResponseEntity.ok(200);
  }


  @PostMapping("/post/comment")
  public ModelAndView postCommentOnPost(
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
      @ModelAttribute(name = "postId") String postId,
      Principal activeUser,
      BindingResult bindingResult) throws IOException {

//    this.commentValidator.validate(postComment, bindingResult);

    if (bindingResult.hasErrors()) {
      return super.redirect("/home");
    }

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);
    this.modelMapper.validate();

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    } else {
      postCommentServiceModel.setCommentPicture(null);
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/home");
  }

  private WallPostServiceModel setDefaultModelAttributes(WallPostServiceModel wallPostServiceModel,
                                                         WallPostBindingModel post) throws IOException {
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
