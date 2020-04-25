package org.java.yasn.services.action;

import java.util.Collection;

import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.models.service.action.NotificationServiceModel;

public interface NotificationService {

  Collection<NotificationServiceModel> findAllByRecipientIdAndSenderId(
      String recipientId, String senderId);

  NotificationServiceModel createNotification(
      String senderId, String recipientId, NotificationType notificationType);

  void removeNotification(String senderId, String recipientUsername, NotificationType friendReq);

  NotificationServiceModel findByRecipientIdSenderIdAndNotificationType(
      String recipientId, String senderId, NotificationType notificationType);
}
