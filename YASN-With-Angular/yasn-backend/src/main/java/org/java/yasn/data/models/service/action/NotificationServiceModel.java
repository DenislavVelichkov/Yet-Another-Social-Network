package org.java.yasn.data.models.service.action;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.models.service.BaseServiceModel;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;

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
  private LocalDate createdOn;
  private String content;
}
