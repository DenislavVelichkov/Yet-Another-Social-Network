package org.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.service.interfaces.CloudinaryService;
import org.yasn.service.interfaces.PostCommentService;
import org.yasn.service.interfaces.WallService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/home/wall")
@AllArgsConstructor
public class WallController extends BaseController {

  private final WallService wallService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;

  @PostMapping("/post")
  public ModelAndView postOnWall(@ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                 @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                                 Principal activeUser) throws IOException {

// TODO: 11/12/2019 Validations

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
  public ModelAndView postCommentOnPost(@ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                        @ModelAttribute(name = "postId") String postId,
                                        Principal activeUser) throws IOException {

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    }else {
      postCommentServiceModel.setCommentPicture(null);
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/home");
  }
}
