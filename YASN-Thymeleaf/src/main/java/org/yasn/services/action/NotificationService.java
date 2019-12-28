package org.yasn.services.action;

import java.util.List;

import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.action.NotificationServiceModel;

public interface NotificationService {

  List<NotificationServiceModel> findAllByRecipientIdAndSenderId(
      String recipientId, String senderId);

  NotificationServiceModel createNotification(
      String senderId, String recipientId, NotificationType notificationType);

  void removeNotification(String senderId, String recipientUsername, NotificationType friendReq);

  NotificationServiceModel findByRecipientIdSenderIdAndNotificationType(
      String recipientId, String senderId, NotificationType notificationType);
}
