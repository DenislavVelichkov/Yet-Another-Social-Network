package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.user.User;
import org.yasn.data.entities.user.UserProfile;
import org.yasn.data.entities.wall.WallPost;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel extends BaseServiceModel {
  private String fullName;
  private User profileOwner;
  private byte[] profilePicture;
  private List<WallPost> wallPosts;
  private Set<UserProfile> friends;
}
