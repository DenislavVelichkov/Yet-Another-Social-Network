package org.yasn.web.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.service.interfaces.NotificationService;
import org.yasn.service.interfaces.WallService;
import org.yasn.web.controller.BaseController;

import java.security.Principal;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActionApiController extends BaseController {
  private final WallService wallService;
  private final NotificationService notificationService;

  @PostMapping("/likes")
  public void likeAction(@ModelAttribute(name = "likePostId") String postId,
                         Principal activeUser) {
    WallPostServiceModel wallPostServiceModel =
        this.wallService.findWallPostById(postId);

    if (this.wallService.isPostLikedByActiveUser(
        activeUser.getName(), wallPostServiceModel.getId())) {
      this.wallService.unlikePost(wallPostServiceModel, activeUser.getName());
    } else {
      this.wallService.likePost(wallPostServiceModel, activeUser.getName());
    }
/*
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
        .orElse(null);*/
  }

  @PostMapping("/add-friend")
  public void sendFriendRequest(@ModelAttribute(name = "profileId") String profileId,
                                Principal activeUser) {

      this.notificationService.createNotification(
          profileId, activeUser.getName(), NotificationType.FRIEND_REQ);

  }

}

