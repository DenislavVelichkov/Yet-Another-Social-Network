package org.yasn.data.entities.user;

import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.Notification;
import org.yasn.data.entities.wall.PostComment;
import org.yasn.data.entities.wall.WallPost;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {

  @OneToMany(
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/entities/user/UserProfile.java
      fetch = FetchType.EAGER,
=======
      fetch = FetchType.LAZY,
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/entities/user/UserProfile.java
=======
      fetch = FetchType.EAGER,
>>>>>>> experimental
      targetEntity = Notification.class,
      mappedBy = "recipient",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  Set<Notification> notifications;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @OneToOne
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      nullable = false, unique = true)
  private User profileOwner;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "cover_picture")
  private String coverPicture;

  @OneToMany(
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/entities/user/UserProfile.java
      fetch = FetchType.EAGER,
=======
      fetch = FetchType.LAZY,
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/entities/user/UserProfile.java
=======
      fetch = FetchType.EAGER,
>>>>>>> experimental
      targetEntity = WallPost.class,
      mappedBy = "postOwner",
      cascade = CascadeType.ALL)
  private Set<WallPost> wallPosts;

  @OneToMany(
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/entities/user/UserProfile.java
      fetch = FetchType.EAGER,
=======
      fetch = FetchType.LAZY,
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/entities/user/UserProfile.java
=======
      fetch = FetchType.EAGER,
>>>>>>> experimental
      targetEntity = PostComment.class,
      mappedBy = "commentOwner",
      cascade = CascadeType.ALL)
  private Set<PostComment> postComments;

  @ManyToMany(
<<<<<<< HEAD
<<<<<<< HEAD:YASN-Thymeleaf/src/main/java/org/yasn/data/entities/user/UserProfile.java
      fetch = FetchType.EAGER,
=======
      fetch = FetchType.LAZY,
>>>>>>> experimental:YASN-With-Angular/yasn-backend/src/main/java/org/yasn/data/entities/user/UserProfile.java
=======
      fetch = FetchType.EAGER,
>>>>>>> experimental
      cascade = CascadeType.PERSIST
  )
  @JoinTable(
      name = "user_profiles_friends",
      joinColumns = @JoinColumn(
          name = "user_profile_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "friend_id",
          referencedColumnName = "id"))
  private Set<UserProfile> friends;

}
