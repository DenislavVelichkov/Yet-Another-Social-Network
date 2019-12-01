package org.yasn.data.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.service.LikeServiceModel;
import org.yasn.data.models.service.PostCommentServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;

import java.sql.Timestamp;
import java.util.Set;

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
