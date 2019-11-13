package org.yasn.data.entities.wall;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wall_posts")
public class WallPost extends BaseEntity {

  @ManyToOne(
      targetEntity = UserProfile.class,
      cascade = CascadeType.ALL
  )
  @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
  private UserProfile createdBy;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "comment_picture")
  private byte[] postPicture;

  @Column(name = "post_content")
  private String postContent;

  @Column(name = "likes_count")
  private long likes;

  @OneToMany(
      mappedBy = "parentPost",
      orphanRemoval = true,
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  private List<PostComment> postComments;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on")
  private Timestamp createdOn;

  @Enumerated(EnumType.STRING)
  @Column(name = "post_privacy")
  private PostPrivacy postPrivacy;
}
