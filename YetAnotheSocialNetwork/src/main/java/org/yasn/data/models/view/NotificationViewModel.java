package org.yasn.data.models.view;

import org.yasn.common.enums.NotificationType;

public class NotificationViewModel extends BaseViewModel {
  private String senderId;
  private UserProfileViewModel recipient;
  private String senderPicture;
  private String senderFullName;
  boolean isViewed;
  private NotificationType notificationType;
}
