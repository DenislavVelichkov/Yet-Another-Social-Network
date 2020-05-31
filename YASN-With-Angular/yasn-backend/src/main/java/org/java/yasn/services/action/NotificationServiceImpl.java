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
import org.java.yasn.web.models.binding.NotificationModel;
import org.java.yasn.web.models.response.NotificationResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

  @Override
  public NotificationResponseModel createNotificationForNewPost(NotificationModel notificationModel) {

    UserProfile recipient = this.userProfileRepository
        .findById(notificationModel.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));
    ;

    Notification notification = new Notification();

    notification.setSender(recipient);
    notification.setNotificationType(NotificationType.CREATED_A_POST);
    notification.setViewed(false);
    notification.setCreatedOn(LocalDateTime.now());
    notification.setSenderPicture(recipient.getProfilePicture());
    notification.setSenderFullName(recipient.getFullName());
    notification.setContent("created a new post");

    Notification newNotification =
        this.notificationRepository.saveAndFlush(notification);

    return this.modelMapper.map(newNotification, NotificationResponseModel.class);
  }

  @Override
  public NotificationResponseModel createFriendRequest(NotificationModel notificationModel) {
    UserProfile recipient = this.userProfileRepository
        .findById(notificationModel.getRecipientId())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));
    UserProfile sender = this.userProfileRepository
        .findById(notificationModel.getRecipientId())
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

    return this.modelMapper.map(
        this.notificationRepository.saveAndFlush(notification), NotificationResponseModel.class);
  }


  @Override
  public void removeNotification(String senderId, String recipientUsername,
                                 NotificationType friendReq) {

  }

  @Override
  public Collection<NotificationResponseModel> getAllNotifications(NotificationModel notificationModel) {

    Optional<UserProfile> currentUser =
        this.userProfileRepository.findById(notificationModel.getSenderId());

    if (currentUser.isEmpty()) {
      throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND);
    }


    Collection<Notification> notifications =
        this.notificationRepository.findBySenderId(notificationModel.getSenderId());

    return notifications.stream()
                        .filter(notification -> this.userProfileRepository
                            .findById(notification.getSender().getId()).get().getFriends().contains(currentUser.get()))
                        .map(notification -> this.modelMapper
                            .map(notification, NotificationResponseModel.class))
                        .collect(Collectors.toCollection(LinkedList::new));
  }

}
