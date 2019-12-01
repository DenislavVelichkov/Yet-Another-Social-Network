package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel extends BaseServiceModel {

  private String fullName;
  private UserServiceModel profileOwner;
  private String profilePicture;
  private String coverPicture;
  private Set<PersonalGalleryServiceModel> photoGallery;
  private Set<WallPostServiceModel> wallPosts;
  private Set<PostCommentServiceModel> postComments;
  private Set<UserProfileServiceModel> friends;
}
