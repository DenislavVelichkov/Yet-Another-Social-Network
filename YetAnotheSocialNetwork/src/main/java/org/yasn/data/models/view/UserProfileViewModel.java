package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.service.UserServiceModel;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileViewModel {
  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private List<WallPostViewModel> wallPosts;
  private Set<UserProfileViewModel> friends;
}
