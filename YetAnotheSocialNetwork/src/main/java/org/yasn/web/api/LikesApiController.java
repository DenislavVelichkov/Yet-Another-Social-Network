package org.yasn.web.api;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.yasn.data.models.service.LikeServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.service.interfaces.UserProfileService;
import org.yasn.service.interfaces.WallService;
import org.yasn.web.api.models.LikeDetailsResponse;
import org.yasn.web.controller.BaseController;

import java.security.Principal;

@AllArgsConstructor
@RestController
public class LikesApiController extends BaseController {
  private final ModelMapper modelMapper;
  private final WallService  wallService;
  private final UserProfileService userProfileService;

  @GetMapping("/api/likes/{postId}")
  public LikeDetailsResponse likeAction(@PathVariable String postId,
                                              Principal activeUser) {
    WallPostServiceModel wallPostServiceModel =
        this.wallService.findWallPostById(postId);

    if (this.wallService.isPostLikedByActiveUser(activeUser.getName())) {
      this.wallService.unlikePost(wallPostServiceModel, activeUser.getName());

    } else {
      this.wallService.likePost(wallPostServiceModel, activeUser.getName());
    }
    UserProfileServiceModel userProfileServiceModel =
        this.userProfileService.findUserProfileByUsername(activeUser.getName());

    LikeServiceModel likeServiceModel = this.wallService
        .findWallPostById(postId)
        .getActualLikes()
        .stream()
        .filter(likeModel ->
            likeModel
                .getId()
                .getProfile().equals(userProfileServiceModel.getId())
                && likeModel
                .getId()
                .getPost().equals(postId))
        .findFirst()
        .orElse(null);

    if (likeServiceModel != null) {
     return this.modelMapper.map(
          likeServiceModel, LikeDetailsResponse.class);
    }

    return null;
  }
}
