package org.yasn.data.entities.wall;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@Entity
@Table(name = "wall_posts")
public class WallPost extends BaseEntity {

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "post_owner_id",
          referencedColumnName = "id")
  private UserProfile postOwner;

  @Column(name = "post_picture")
  private String postPicture;

  @Column(name = "post_content")
  private String postContent;

  @OneToMany(
          targetEntity = PostComment.class,
          mappedBy = "parentPost",
          cascade = CascadeType.ALL)
  private Set<PostComment> comments;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on")
  private Timestamp createdOn;

  @Enumerated(EnumType.STRING)
  @Column(name = "post_privacy")
  private PostPrivacy postPrivacy;

  @OneToMany(
          targetEntity = Like.class,
          mappedBy = "likeOwner",
          cascade = CascadeType.ALL,
          orphanRemoval = true)
  private Set<Like> actualLikes;

  @Column(name = "location")
  private String location;
}
