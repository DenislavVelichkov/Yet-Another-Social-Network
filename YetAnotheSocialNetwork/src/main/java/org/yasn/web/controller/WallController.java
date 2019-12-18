package org.yasn.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.wall.PostCommentServiceModel;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.services.CloudinaryService;
import org.yasn.services.wall.PostCommentService;
import org.yasn.services.wall.WallService;
import org.yasn.validation.wall.CommentValidator;
import org.yasn.validation.wall.PostValidator;

@Controller
@RequestMapping("/home/wall")
@AllArgsConstructor
public class WallController extends BaseController {

  private final WallService wallService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;
  private final PostValidator postValidator;
  private final CommentValidator commentValidator;

  @PostMapping("/post")
  public ModelAndView postOnWall(
      @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
      Principal activeUser,
      BindingResult bindingResult) throws IOException {

    this.postValidator.validate(wallPost, bindingResult);

    if (bindingResult.hasErrors()) {
      return super.redirect("/home");
    }

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);

    if (!wallPost.getPostPicture().isEmpty()) {
      wallPostServiceModel.setPostPicture(
          this.cloudinaryService.uploadImage(wallPost.getPostPicture()));
    } else {
      wallPostServiceModel.setPostPicture(null);
    }

    if (wallPostServiceModel.getPostPrivacy() == null) {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    this.wallService.createPost(wallPostServiceModel, activeUser.getName());

    return super.redirect("/home");
  }

  @PostMapping("/comment")
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
}
