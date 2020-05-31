package org.java.yasn.services.action;

import java.util.Collection;

import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.web.models.binding.NotificationModel;
import org.java.yasn.web.models.response.NotificationResponseModel;

public interface NotificationService {

  NotificationResponseModel createNotificationForNewPost(NotificationModel notificationModel);

  NotificationResponseModel createFriendRequest(NotificationModel notificationModel);

  void removeNotification(String senderId, String recipientUsername, NotificationType friendReq);

  Collection<NotificationResponseModel> getAllNotifications(NotificationModel notificationModel);
}
