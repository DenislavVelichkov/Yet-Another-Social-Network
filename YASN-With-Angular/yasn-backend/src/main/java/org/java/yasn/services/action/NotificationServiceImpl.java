package org.java.yasn.services.action;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.ExceptionMessages;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.Notification;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.repository.action.NotificationRepository;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.response.NotificationResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

  @Override
  public NotificationResponseModel createNotificationForNewPost(ActionModel actionModel) {

    UserProfile recipient = this.userProfileRepository
        .findById(actionModel.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));


    Notification notification = new Notification();

    notification.setSender(recipient);
    notification.setNotificationType(NotificationType.CREATED_A_POST);
    notification.setViewed(false);
    notification.setCreatedOn(LocalDateTime.now());
    notification.setSenderPicture(recipient.getProfilePicture());
    notification.setSenderFullName(recipient.getFullName());
    notification.setContent("created a new post");

    Optional<Notification> isNotificationAlreadyExists =
        this.notificationRepository.findBySenderIdAndRecipientIdAndNotificationType(notification.getSender().getId(), notification.getRecipient().getId(), notification.getNotificationType());

    if (isNotificationAlreadyExists.isPresent()) {
      throw new IllegalArgumentException("You can't send the same request twice!");
    }

    Notification newNotification =
        this.notificationRepository.saveAndFlush(notification);

    return this.modelMapper.map(newNotification, NotificationResponseModel.class);
  }

  @Override
  public NotificationResponseModel createFriendRequest(ActionModel actionModel) {
    UserProfile recipient = this.userProfileRepository
        .findById(actionModel.getRecipientId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));
    UserProfile sender = this.userProfileRepository
        .findById(actionModel.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

    Notification notification = new Notification();
    notification.setSenderFullName(sender.getFullName());
    notification.setSender(sender);
    notification.setRecipient(recipient);
    notification.setViewed(false);
    notification.setSenderPicture(sender.getProfilePicture());
    notification.setContent("wants to be your friend");
    notification.setCreatedOn(LocalDateTime.now());
    notification.setNotificationType(NotificationType.FRIEND_REQ);

    Optional<Notification> isNotificationAlreadyExists =
        this.notificationRepository.findBySenderIdAndRecipientIdAndNotificationType(notification.getSender().getId(), notification.getRecipient().getId(), notification.getNotificationType());

    if (isNotificationAlreadyExists.isPresent()) {
      throw new IllegalArgumentException("You can't send the same request twice!");
    }

    return this.modelMapper.map(
        this.notificationRepository.saveAndFlush(notification), NotificationResponseModel.class);
  }


  @Override
  public void removeNotification(String senderId, String recipientUsername,
                                 NotificationType friendReq) {

  }

  @Override
  public Collection<NotificationResponseModel> getAllNotifications(ActionModel actionModel) {

    Collection<Notification> notifications =
        this.notificationRepository.findByRecipientId(actionModel.getRecipientId());

    return notifications.stream()
                        .map(notification -> this.modelMapper.map(notification, NotificationResponseModel.class))
                        .collect(Collectors.toCollection(LinkedList::new));
  }

}
