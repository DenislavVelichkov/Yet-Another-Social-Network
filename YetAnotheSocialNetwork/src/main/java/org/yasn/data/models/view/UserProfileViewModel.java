package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.UserServiceModel;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileViewModel extends BaseViewModel{

  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private Set<PersonalGalleryViewModel> photoGallery;
  private Set<PostCommentViewModel> postComments;
  private Set<WallPostViewModel> wallPosts;
  private Set<UserProfileViewModel> friends;
}
