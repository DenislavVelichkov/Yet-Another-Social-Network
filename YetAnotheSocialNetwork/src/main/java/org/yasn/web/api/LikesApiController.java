package org.yasn.web.api;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.service.interfaces.WallService;
import org.yasn.web.controller.BaseController;

import java.security.Principal;

@AllArgsConstructor
@RestController
public class LikesApiController extends BaseController {
  private final ModelMapper modelMapper;
  private final WallService  wallService;

  @GetMapping("/api/likes/{postId}")
  public String likeAction(@PathVariable String postId,
                           Principal activeUser) {
    WallPostServiceModel wallPostServiceModel =
        this.wallService.findWallPostById(postId);

    wallPostServiceModel.setLikes(wallPostServiceModel.getLikes() + 1);
    this.wallService.likePost(wallPostServiceModel, activeUser.getName());

   return "redirect:/home";
  }
}
