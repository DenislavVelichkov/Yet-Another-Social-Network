package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.entities.wall.WallPost;
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
  private List<WallPost> wallPosts;
  private Set<UserProfile> friends;
}
