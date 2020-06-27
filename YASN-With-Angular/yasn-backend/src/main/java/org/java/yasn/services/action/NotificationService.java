package org.java.yasn.services.action;

import java.util.Collection;

import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.response.NotificationResponseModel;

public interface NotificationService {

  NotificationResponseModel createNotificationForNewPost(ActionModel actionModel);

  NotificationResponseModel createFriendRequest(ActionModel actionModel);

  void removeNotification(String senderId, String recipientUsername, NotificationType friendReq);

  Collection<NotificationResponseModel> getAllNotifications(ActionModel actionModel);
}
