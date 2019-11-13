package org.yasn.data.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;
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

  @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User profileOwner;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "profile_picture")
  private byte[] profilePicture;

  @OneToMany(
      mappedBy = "createdBy",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<WallPost> wallPosts;

  @OneToOne(targetEntity = Avatar.class, cascade = CascadeType.ALL)
  private Avatar avatar;

  @ManyToMany(
      cascade = CascadeType.ALL
  )
  @JoinTable(
      name = "user_profiles_friends",
      joinColumns = @JoinColumn(
          name = "user_profile_id"),
      inverseJoinColumns = @JoinColumn(name = "friend_id"))
  private Set<UserProfile> friends;
}
