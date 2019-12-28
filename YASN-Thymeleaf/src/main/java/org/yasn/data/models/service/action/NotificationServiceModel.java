package org.yasn.data.models.service.action;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.BaseServiceModel;
import org.yasn.data.models.service.user.UserProfileServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class NotificationServiceModel extends BaseServiceModel {
  boolean isViewed;
  private String senderId;
  private UserProfileServiceModel recipient;
  private String senderPicture;
  private String senderFullName;
  private NotificationType notificationType;
  private Timestamp createdOn;
  private String content;
}
