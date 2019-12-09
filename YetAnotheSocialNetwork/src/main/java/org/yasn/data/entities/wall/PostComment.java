package org.yasn.data.entities.wall;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class PostComment extends BaseEntity {

  @ManyToOne(targetEntity = WallPost.class)
  @JoinColumn(
      name = "parent_post_id",
      referencedColumnName = "id")
  private WallPost parentPost;

  @ManyToOne(targetEntity = UserProfile.class)
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
