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
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.service.interfaces.WallService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/home/wall")
public class WallPostController extends BaseController {

  private final WallService wallService;
  private final ModelMapper modelMapper;

  @Autowired
  public WallPostController(WallService wallService,
                            ModelMapper modelMapper) {
    this.wallService = wallService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/post")
  public ModelAndView postOnWall(ModelAndView modelAndView,
                                 @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
                                 @ModelAttribute(name = "postComment") PostCommentBindingModel postComment,
                                 Principal activeUser) throws IOException {

// TODO: 11/12/2019 Validations

    WallPostServiceModel wallPostService =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);
    wallPostService.setPostPicture(wallPost.getPostPicture().getBytes());

    if (wallPostService.getPostPrivacy() == null) {
      wallPostService.setPostPrivacy(PostPrivacy.PUBLIC);
    }

    this.wallService.createPost(wallPostService, activeUser);

    return super.redirect("/home");
  }

}
