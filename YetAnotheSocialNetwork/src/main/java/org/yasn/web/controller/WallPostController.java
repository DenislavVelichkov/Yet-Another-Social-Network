package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WallPostController extends BaseController {

  private final WallService wallService;
  private final PostCommentService postCommentService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;

  @Autowired
  public WallPostController(WallService wallService,
                            PostCommentService postCommentService,
                            ModelMapper modelMapper,
                            CloudinaryService cloudinaryService) {
    this.wallService = wallService;
    this.postCommentService = postCommentService;
    this.modelMapper = modelMapper;
    this.cloudinaryService = cloudinaryService;
  }

  @PostMapping("/post")
  public ModelAndView postOnWall(ModelAndView modelAndView,
                                 @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                 @ModelAttribute(name = "commentPost") PostCommentBindingModel postComment,
                                 Principal activeUser) throws IOException {

// TODO: 11/12/2019 Validations

    WallPostServiceModel wallPostServiceModel =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);

    if (!wallPost.getPostPicture().isEmpty()) {
      wallPostServiceModel.setPostPicture(
          this.cloudinaryService.uploadImage(wallPost.getPostPicture()));
    }

    if (wallPostServiceModel.getPostPrivacy() == null) {
      wallPostServiceModel.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    this.wallService.createPost(wallPostServiceModel, activeUser);

    return super.redirect("/home");
  }

  @PostMapping("/comment")
  public ModelAndView postCommentOnPost(ModelAndView modelAndView,
                                        @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                        @ModelAttribute(name = "postId") String postId,
                                        Principal activeUser) throws IOException {

    PostCommentServiceModel postCommentServiceModel =
        this.modelMapper.map(postComment, PostCommentServiceModel.class);

    if (!postComment.getCommentPicture().isEmpty()) {
      postCommentServiceModel.setCommentPicture(
          this.cloudinaryService.uploadImage(postComment.getCommentPicture()));
    }

    this.postCommentService.postComment(postCommentServiceModel, activeUser, postId);

    return super.redirect("/home");
  }
}
