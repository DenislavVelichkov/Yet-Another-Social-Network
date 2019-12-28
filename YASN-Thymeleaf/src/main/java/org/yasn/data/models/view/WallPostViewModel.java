package org.yasn.data.models.view;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.PostPrivacy;
import org.yasn.data.models.service.action.LikeServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class WallPostViewModel extends BaseViewModel {
  private UserProfileViewModel postOwner;
  private String postPicture;
  private String postContent;
  private Set<PostCommentViewModel> comments;
  private Timestamp createdOn;
  private PostPrivacy postPrivacy;
  private Set<LikeServiceModel> actualLikes;
  private String location;
}
