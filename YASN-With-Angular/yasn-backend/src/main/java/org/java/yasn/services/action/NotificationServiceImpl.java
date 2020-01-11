package org.java.yasn.services.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.NotificationMessages;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.Notification;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.data.models.service.action.NotificationServiceModel;
import org.java.yasn.data.models.service.user.UserProfileServiceModel;
import org.java.yasn.repository.action.NotificationRepository;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.services.user.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserProfileRepository userProfileRepository;
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;

  @Override
  public NotificationServiceModel createNotification(
      String recipientId, String senderUsername, NotificationType notificationType) {

    UserProfileServiceModel sender =
        this.userProfileService
            .findUserProfileByUsername(senderUsername);

    UserProfileServiceModel recipient =
        this.userProfileService.findUserProfileById(recipientId);

    NotificationServiceModel notificationServiceModel =
        new NotificationServiceModel();

    notificationServiceModel.setSenderId(sender.getId());
    notificationServiceModel.setRecipient(recipient);
    notificationServiceModel.setNotificationType(notificationType);
    notificationServiceModel.setViewed(false);
    notificationServiceModel.setSenderFullName(sender.getFullName());
    notificationServiceModel.setSenderPicture(sender.getProfilePicture());
    notificationServiceModel.setCreatedOn(new Timestamp(new Date().getTime()));

    switch (notificationType) {
      case FRIEND_REQ:
        notificationServiceModel.setContent(NotificationMessages.FRIEND_REQUEST);
        break;
      case MESSAGE:
        break;
      default:
        break;
    }

    Notification notification =
        this.modelMapper.map(notificationServiceModel, Notification.class);
    this.modelMapper.validate();
    this.notificationRepository.saveAndFlush(notification);

    return this.modelMapper.map(notification, NotificationServiceModel.class);
  }

  @Override
  public List<NotificationServiceModel> findAllByRecipientIdAndSenderId(String recipientId,
                                                                        String senderId) {

    List<Notification> notifications =
        this.notificationRepository.findAllByRecipientIdAndSenderId(recipientId, senderId);

    return notifications
        .stream()
        .map(notification -> this.modelMapper.map(
            notification, NotificationServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void removeNotification(String senderId, String recipientUsername,
                                 NotificationType friendReq) {
    UserProfileServiceModel recipient =
        this.userProfileService.findUserProfileByUsername(recipientUsername);

    NotificationServiceModel notificationToRemove =
        recipient.getNotifications().stream()
                 .filter(notification ->
                     notification.getRecipient().getId().equals(recipient.getId())
                         && notification.getSenderId().equals(senderId)
                         && notification.getNotificationType().equals(friendReq))
                 .findFirst()
                 .orElse(null);

    if (notificationToRemove != null) {
      recipient.getNotifications().remove(notificationToRemove);
      UserProfile userProfile = this.modelMapper.map(recipient, UserProfile.class);
      this.modelMapper.validate();
      this.userProfileRepository.saveAndFlush(userProfile);
    }
  }

  @Override
  public NotificationServiceModel findByRecipientIdSenderIdAndNotificationType(
      String recipientId, String senderId, NotificationType notificationType) {

    Notification notification =
        this.notificationRepository.findByRecipientIdAndSenderIdAndNotificationType(
            recipientId, senderId, notificationType).orElse(null);

    if (notification != null) {
      return this.modelMapper.map(notification, NotificationServiceModel.class);
    }

    return null;
  }
}
