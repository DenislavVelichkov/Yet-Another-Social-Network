package org.yasn.service.interfaces;

import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.NotificationServiceModel;

public interface NotificationService {
  NotificationServiceModel createNotification(
      String senderId, String recipientId, NotificationType notificationType);

  boolean doesNotificationExists(
      String profileId, String recipientUsername, NotificationType notificationType);
}
