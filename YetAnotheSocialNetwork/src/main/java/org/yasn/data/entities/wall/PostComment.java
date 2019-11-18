package org.yasn.data.entities.wall;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post_comments")
public class PostComment extends BaseEntity {

  @ManyToOne(targetEntity = WallPost.class)
  @JoinColumn(name = "wall_post_id", referencedColumnName = "id")
  private WallPost parentPost;

  @Column(name = "comment_content")
  private String commentContent;

  @Column(name = "post_liked")
  private boolean isPostLiked;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "comment_picture")
  private byte[] commentPicture;
}
