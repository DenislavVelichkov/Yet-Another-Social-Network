package org.java.yasn.services.action;

import java.time.LocalDate;
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
  public NotificationResponseModel createNotification(NotificationModel notificationModel) {

    Optional<UserProfile> recipient =
        this.userProfileRepository.findById(notificationModel.getSenderId());

    Notification notification = new Notification();

    notification.setSender(recipient.get());
    notification.setNotificationType(NotificationType.getNotificationType(notificationModel.getNotificationType()));
    notification.setViewed(false);
    notification.setCreatedOn(LocalDate.now());
    notification.setSenderPicture(recipient.get().getProfilePicture());
    notification.setSenderFullName(recipient.get().getFullName());
    notification.setContent(notificationModel.getContent());

    Notification newNotification =
        this.notificationRepository.saveAndFlush(notification);

    return this.modelMapper.map(newNotification, NotificationResponseModel.class);
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
