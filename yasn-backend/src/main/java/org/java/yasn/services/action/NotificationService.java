package org.java.yasn.services.action;

import java.util.Collection;
import java.util.Map;

import org.java.yasn.data.entities.Notification;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.response.NotificationResponseModel;

public interface NotificationService {

  NotificationResponseModel createNotificationForNewPost(Map<String, String> sender);

  NotificationResponseModel createFriendRequest(ActionModel actionModel);

  Collection<NotificationResponseModel> getAllNotifications(String recipientId);

  boolean checkForPendingRequest(String viewerId, String selectedProfileId, String notificationType);

  Notification getNotification(String viewerId, String selectedProfileId, String notificationType);

  void deleteNotification(String notificationId);

  void markNotificationAsRead(String notificationId);
}
