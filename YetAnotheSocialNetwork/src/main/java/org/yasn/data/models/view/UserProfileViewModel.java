package org.yasn.data.models.view;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.UserServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileViewModel extends BaseViewModel {

  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private PersonalGalleryViewModel personalGallery;
  private Set<PostCommentViewModel> postComments;
  private Set<WallPostViewModel> wallPosts;
  private Set<UserProfileViewModel> friends;
  private Set<NotificationViewModel> notifications;

}
