package org.yasn.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.yasn.common.NotificationMessages;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.Notification;
import org.yasn.data.models.service.NotificationServiceModel;
import org.yasn.data.models.service.UserProfileServiceModel;
import org.yasn.repository.NotificationRepository;
import org.yasn.service.interfaces.NotificationService;
import org.yasn.service.interfaces.UserProfileService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository notificationRepository;
  private final UserProfileService userProfileService;
  private final ModelMapper modelMapper;

  @Override
  public NotificationServiceModel createNotification(
      String recipientId, String senderUsername, NotificationType notificationType) {

    UserProfileServiceModel sender  =
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
  public List<NotificationServiceModel> findAllByRecipientIdAndSenderId(String recipientId, String senderId) {

    List<Notification> notifications = this.notificationRepository
        .findAllByRecipientIdAndSenderId(recipientId, senderId);

    return notifications
        .stream()
        .map(notification -> this.modelMapper.map(
            notification, NotificationServiceModel.class))
        .collect(Collectors.toList());
  }
}
