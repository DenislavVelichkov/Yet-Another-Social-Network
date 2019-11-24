package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.UserServiceModel;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileViewModel {
  private String id;
  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private Set<String> photoGallery;
  private Set<WallPostViewModel> wallPosts;
  private Set<PostCommentViewModel> postComments;
  private Set<UserProfileViewModel> friends;
}
