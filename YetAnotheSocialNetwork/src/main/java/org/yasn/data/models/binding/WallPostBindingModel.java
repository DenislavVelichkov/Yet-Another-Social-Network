package org.yasn.data.models.binding;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.service.action.LikeServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;
import org.yasn.data.models.service.wall.PostCommentServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class WallPostBindingModel {
  private String id;
  private UserProfileServiceModel postOwner;
  private MultipartFile postPicture;
  private String postContent;
  private Set<PostCommentServiceModel> comments;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
  private Set<LikeServiceModel> actualLikes;
  private String location;

}
