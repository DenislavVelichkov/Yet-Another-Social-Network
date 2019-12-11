package org.yasn.data.models.service;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel extends BaseServiceModel {

  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private PersonalGalleryServiceModel personalGalleryServiceModel;
  private Set<WallPostServiceModel> wallPosts;
  private Set<PostCommentServiceModel> postComments;
  private Set<UserProfileServiceModel> friends;
  private Set<NotificationServiceModel> notifications;
}
