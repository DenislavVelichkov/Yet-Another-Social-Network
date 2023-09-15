package org.java.yasn.services.action;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
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
  public NotificationResponseModel createNotificationForNewPost(Map<String, String> model) {

    UserProfile sender = this.userProfileRepository
        .findById(model.get("senderId"))
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));


    Notification notification = new Notification();

    notification.setSender(sender);
    notification.setNotificationType(NotificationType.CREATED_A_POST);
    notification.setViewed(false);
    notification.setCreatedOn(LocalDateTime.now());
    notification.setSenderPicture(sender.getProfilePicture());
    notification.setSenderFullName(sender.getFullName());
    notification.setContent(NotificationType.CREATED_A_POST.getLabel());

    this.userProfileRepository.findById(model.get("senderId"))
                              .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND))
                              .getFriends()
                              .forEach(f -> {

                                notification.setRecipient(f);

                                this.notificationRepository.saveAndFlush(notification);
                              });

    return this.modelMapper.map(notification, NotificationResponseModel.class);
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
    notification.setContent(NotificationType.FRIEND_REQ.getLabel());
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
  public Collection<NotificationResponseModel> getAllNotifications(String recipientId) {

    Collection<Notification> notifications =
        this.notificationRepository.findByRecipientId(recipientId);

    return notifications.stream()
                        .map(notification -> this.modelMapper.map(notification, NotificationResponseModel.class))
                        .collect(Collectors.toCollection(LinkedList::new));
  }

  @Override
  public boolean checkForPendingRequest(String viewerId, String selectedProfileId, String notificationType) {
    Optional<Notification> isRequestExists = this.notificationRepository.findBySenderIdAndRecipientIdAndNotificationType(viewerId, selectedProfileId, NotificationType.getNotificationType(notificationType));

    return isRequestExists.isPresent();
  }

  @Override
  public Notification getNotification(String viewerId, String selectedProfileId, String notificationType) {

    Notification notification;

    Optional<Notification> normalSearchForNotification =
        this.notificationRepository.findBySenderIdAndRecipientIdAndNotificationType(
            viewerId, selectedProfileId, NotificationType.getNotificationType(notificationType));

    Optional<Notification> reverseSearchForNotification =
        this.notificationRepository.findBySenderIdAndRecipientIdAndNotificationType(
            selectedProfileId, viewerId, NotificationType.getNotificationType(notificationType));

    if (normalSearchForNotification.isEmpty() && reverseSearchForNotification.isEmpty()) {
      throw new IllegalArgumentException(ExceptionMessages.NOTIFICATION_DOES_NOT_EXISTS);
    } else {
      notification = normalSearchForNotification.orElseGet(reverseSearchForNotification::get);
    }

    return notification;
  }

  @Override
  public void deleteNotification(String notificationId) {
    Notification notificationToBeRemoved = this.notificationRepository.findById(notificationId)
                                                                      .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.NOTIFICATION_DOES_NOT_EXISTS));

    this.notificationRepository.delete(notificationToBeRemoved);

  }

  @Override
  public void markNotificationAsRead(String notificationId) {
    Notification notificationToEdit =
        this.notificationRepository.findById(notificationId)
                                   .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.NOTIFICATION_DOES_NOT_EXISTS));
    notificationToEdit.setViewed(true);
    this.notificationRepository.saveAndFlush(notificationToEdit);
  }

}
