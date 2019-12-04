package org.yasn.data.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;

@Getter
@Setter
@NoArgsConstructor
public class NotificationServiceModel extends BaseServiceModel{
  private String senderId;
  private UserProfileServiceModel recipient;
  private String senderPicture;
  private String senderFullName;
  boolean isViewed;
  private NotificationType notificationType;
}
