package org.yasn.data.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.wall.PostComment;
import org.yasn.data.entities.wall.WallPost;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {

  @Column(name = "full_name")
  private String fullName;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User profileOwner;

  @Column(name = "profile_picture")
  private String profilePicture;

  @OneToMany(
      targetEntity = WallPost.class,
      cascade = CascadeType.ALL,
      mappedBy = "postOwner")
  private List<WallPost> wallPosts;

  @OneToMany(
      targetEntity = PostComment.class,
      fetch = FetchType.EAGER,
      mappedBy = "commentOwner",
      cascade = CascadeType.ALL)
  private List<PostComment> comments;

  @ManyToMany(
      cascade = CascadeType.ALL
  )
  @JoinTable(
      name = "user_profiles_friends",
      joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
  private Set<UserProfile> friends;
}
