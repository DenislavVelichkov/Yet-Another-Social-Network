package org.yasn.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
                                 Principal activeUser) throws IOException {

    WallPostServiceModel wallPostService =
        this.modelMapper.map(wallPost, WallPostServiceModel.class);

    this.wallService.createPost(wallPostService, activeUser);

    return super.redirect("/home");
  }

}
