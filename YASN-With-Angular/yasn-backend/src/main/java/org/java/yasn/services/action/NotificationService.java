package org.java.yasn.services.action;

import java.util.Collection;

import org.java.yasn.data.models.service.action.NotificationServiceModel;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.response.NotificationResponseModel;

public interface NotificationService {

  NotificationResponseModel createNotificationForNewPost(ActionModel actionModel);

  NotificationResponseModel createFriendRequest(ActionModel actionModel);

  Collection<NotificationResponseModel> getAllNotifications(ActionModel actionModel);

  boolean checkForPendingRequest(String viewerId, String selectedProfileId, String notificationType);

  NotificationServiceModel getNotification(String viewerId, String selectedProfileId, String notificationType);

  void deleteNotification(String notificationId);
}
