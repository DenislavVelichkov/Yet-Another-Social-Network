package org.yasn.domain.entities.user;

import org.yasn.domain.entities.BaseEntity;
import org.yasn.domain.entities.wall.WallPost;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {
  private String fullName;
  private User profileOwner;
  private byte[] profilePicture;
  private List<WallPost> wallPosts;
  private Set<UserProfile> friendOf;
  private Set<UserProfile> friends;

  @Column(name = "full_name")
  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  public User getProfileOwner() {
    return this.profileOwner;
  }

  public void setProfileOwner(User profileOwner) {
    this.profileOwner = profileOwner;
  }

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "profile_picture")
  public byte[] getProfilePicture() {
    return this.profilePicture;
  }

  public void setProfilePicture(byte[] profilePicture) {
    this.profilePicture = profilePicture;
  }

  @OneToMany(
      mappedBy = "createdBy",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  public List<WallPost> getWallPosts() {
    return this.wallPosts;
  }

  public void setWallPosts(List<WallPost> wallPosts) {
    this.wallPosts = wallPosts;
  }

  @ManyToMany(
      cascade = CascadeType.ALL
  )
  @JoinTable(name = "friend_of_friends",
      joinColumns =
          {@JoinColumn(name = "friend_of_id")},
      inverseJoinColumns = {@JoinColumn(name = "friend_id")})
  public Set<UserProfile> getFriends() {
    return this.friends;
  }

  public void setFriends(Set<UserProfile> friends) {
    this.friends = friends;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "friend_of_friends",
      joinColumns =
          {@JoinColumn(name = "friend_id")},
      inverseJoinColumns = {@JoinColumn(name = "friend_of_id")})
  public Set<UserProfile> getFriendOf() {
    return this.friendOf;
  }

  public void setFriendOf(Set<UserProfile> friendOf) {
    this.friendOf = friendOf;
  }
}
