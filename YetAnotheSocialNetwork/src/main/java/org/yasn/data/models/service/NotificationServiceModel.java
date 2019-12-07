package org.yasn.data.models.service;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;

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
