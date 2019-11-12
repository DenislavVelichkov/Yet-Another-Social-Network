package org.yasn.data.entities.wall;

import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.entities.BaseEntity;
import org.yasn.data.entities.user.UserProfile;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "wall_posts")
public class WallPost extends BaseEntity {
  private UserProfile createdBy;
  private byte[] postPicture;
  private String postContent;
  private long likes;
  private List<PostComment> postComments;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;

  public WallPost() {
  }

  @ManyToOne(
      targetEntity = UserProfile.class,
      cascade = CascadeType.ALL
  )
  @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
  public UserProfile getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(UserProfile createdBy) {
    this.createdBy = createdBy;
  }

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "comment_picture")
  public byte[] getPostPicture() {
    return this.postPicture;
  }

  public void setPostPicture(byte[] postPicture) {
    this.postPicture = postPicture;
  }

  @Column(name = "post_content")
  public String getPostContent() {
    return this.postContent;
  }

  public void setPostContent(String postContent) {
    this.postContent = postContent;
  }

  @Column(name = "likes_count")
  public long getLikes() {
    return this.likes;
  }

  public void setLikes(long likes) {
    this.likes = likes;
  }

  @OneToMany(
      mappedBy = "parentPost",
      orphanRemoval = true,
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  public List<PostComment> getPostComments() {
    return this.postComments;
  }

  public void setPostComments(List<PostComment> postComments) {
    this.postComments = postComments;
  }

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on")
  public Timestamp getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "post_privacy")
  public PostPrivacy getPostPrivacy() {
    return this.postPrivacy;
  }

  public void setPostPrivacy(PostPrivacy postPrivacy) {
    this.postPrivacy = postPrivacy;
  }
}
