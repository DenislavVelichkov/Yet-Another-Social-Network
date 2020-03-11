package org.yasn.data.entities.wall;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wall_posts")
public class WallPost extends BaseEntity {

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(
      name = "post_owner_id",
      referencedColumnName = "id",
      nullable = false)
  private UserProfile postOwner;

  @Column(name = "post_picture")
  private String postPicture;

  @Column(
      name = "post_content",
      nullable = false)
  private String postContent;

  @OneToMany(
      fetch = FetchType.EAGER,
      targetEntity = PostComment.class,
      mappedBy = "parentPost",
      cascade = CascadeType.ALL)
  private Set<PostComment> comments;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(
      name = "created_on",
      nullable = false)
  private Timestamp createdOn;

  @Enumerated(EnumType.STRING)
  @Column(name = "post_privacy", nullable = false)
  private PostPrivacy postPrivacy;

  @OneToMany(
      fetch = FetchType.EAGER,
      targetEntity = Like.class,
      mappedBy = "likeOwner",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<Like> actualLikes;

  @Column(name = "location")
  private String location;
}
