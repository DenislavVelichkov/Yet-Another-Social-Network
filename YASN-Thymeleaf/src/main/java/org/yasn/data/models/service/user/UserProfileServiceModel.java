package org.yasn.data.models.service.user;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.action.NotificationServiceModel;
import org.yasn.data.models.service.wall.PostCommentServiceModel;
import org.yasn.data.models.service.wall.WallPostServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel extends BaseServiceModel {

  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private Set<WallPostServiceModel> wallPosts;
  private Set<PostCommentServiceModel> postComments;
  private Set<UserProfileServiceModel> friends;
  private Set<NotificationServiceModel> notifications;
}
