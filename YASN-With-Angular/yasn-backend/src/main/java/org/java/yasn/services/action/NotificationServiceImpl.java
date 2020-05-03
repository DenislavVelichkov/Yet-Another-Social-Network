package org.java.yasn.services.action;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.Notification;
import org.java.yasn.data.entities.user.UserProfile;
import org.java.yasn.repository.action.NotificationRepository;
import org.java.yasn.repository.user.UserProfileRepository;
import org.java.yasn.web.models.binding.NotificationModel;
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
  public NotificationResponseModel createNotification(NotificationModel notificationModel) {

    Optional<UserProfile> recipient =
        this.userProfileRepository.findById(notificationModel.getRecipientId());

    Notification notification = new Notification();

    notification.setRecipient(recipient.get());
    notification.setNotificationType(NotificationType.getNotificationType(notificationModel.getNotificationType()));
    notification.setViewed(false);
    notification.setCreatedOn(LocalDate.now());
    notification.setRecipientPicture(recipient.get().getProfilePicture());
    notification.setRecipientFullName(recipient.get().getFullName());
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
    Collection<Notification> notifications = this.notificationRepository.findByRecipientId(notificationModel.getRecipientId());

    return notifications.stream()
                        .map(notification -> this.modelMapper
                            .map(notification, NotificationResponseModel.class))
                        .collect(Collectors.toCollection(LinkedList::new));
  }

}
