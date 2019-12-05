package org.yasn.service.interfaces;

import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.service.NotificationServiceModel;

import java.util.List;

public interface NotificationService {

  List<NotificationServiceModel> findAllByRecipientIdAndSenderId(String recipientId, String senderId);

  NotificationServiceModel createNotification(
      String senderId, String recipientId, NotificationType notificationType);

  void removeNotification(String senderId, String recipientUsername, NotificationType friendReq);
}
