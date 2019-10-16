package org.yasn.domain.models.service;

import org.yasn.domain.entities.user.User;
import org.yasn.domain.entities.user.UserProfile;
import org.yasn.domain.entities.wall.WallPost;

import java.util.List;
import java.util.Set;

public class UserProfileServiceModel {
  private String fullName;
  private User profileOwner;
  private byte[] profilePicture;
  private List<WallPost> wallPosts;
  private Set<UserProfile> friendOf;
  private Set<UserProfile> friends;

  public UserProfileServiceModel() {
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public User getProfileOwner() {
    return this.profileOwner;
  }

  public void setProfileOwner(User profileOwner) {
    this.profileOwner = profileOwner;
  }

  public byte[] getProfilePicture() {
    return this.profilePicture;
  }

  public void setProfilePicture(byte[] profilePicture) {
    this.profilePicture = profilePicture;
  }

  public List<WallPost> getWallPosts() {
    return this.wallPosts;
  }

  public void setWallPosts(List<WallPost> wallPosts) {
    this.wallPosts = wallPosts;
  }

  public Set<UserProfile> getFriendOf() {
    return this.friendOf;
  }

  public void setFriendOf(Set<UserProfile> friendOf) {
    this.friendOf = friendOf;
  }

  public Set<UserProfile> getFriends() {
    return this.friends;
  }

  public void setFriends(Set<UserProfile> friends) {
    this.friends = friends;
  }
}
