package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel extends BaseServiceModel {
  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private List<PostCommentServiceModel> comments;
  private List<WallPostServiceModel> wallPosts;
  private Set<UserProfileServiceModel> friends;
}
