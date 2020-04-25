package org.java.yasn.data.entities.wall;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.data.entities.BaseEntity;
import org.java.yasn.data.entities.user.UserProfile;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class PostComment extends BaseEntity {

  @ManyToOne(targetEntity = WallPost.class,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "parent_post_id",
      referencedColumnName = "id")
  private WallPost parentPost;

  @ManyToOne(targetEntity = UserProfile.class,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "comment_owner_id",
      referencedColumnName = "id")
  private UserProfile commentOwner;

  @Column(
      name = "comment_content",
      nullable = false)
  private String commentContent;

  @Column(name = "comment_picture")
  private String commentPicture;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on",
      updatable = false, nullable = false)
  private Timestamp createdOn;
}
