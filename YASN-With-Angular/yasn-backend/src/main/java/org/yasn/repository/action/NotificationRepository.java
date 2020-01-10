package org.yasn.repository.action;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

  List<Notification> findAllByRecipientIdAndSenderId(String recipientId, String senderId);

  Optional<Notification> findByRecipientIdAndSenderIdAndNotificationType(
      String recipientId, String senderId, NotificationType notificationType);
}
