package org.yasn.data.entities.wall;

import org.yasn.data.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "post_comments")
public class PostComment extends BaseEntity {
  private WallPost parentPost;
  private String commentContent;

  public PostComment() {
  }

  @ManyToOne(targetEntity = WallPost.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "wall_post_id", referencedColumnName = "id")
  public WallPost getParentPost() {
    return this.parentPost;
  }

  public void setParentPost(WallPost parentPost) {
    this.parentPost = parentPost;
  }

  @Column(name = "comment_content")
  public String getCommentContent() {
    return this.commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }
}
