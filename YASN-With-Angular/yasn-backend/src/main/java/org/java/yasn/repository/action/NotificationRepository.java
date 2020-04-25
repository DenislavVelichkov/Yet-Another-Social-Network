package org.java.yasn.repository.action;

import java.util.Collection;
import java.util.Optional;

import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

  Collection<Notification> findAllByRecipientIdAndSenderId(String recipientId, String senderId);

  Optional<Notification> findByRecipientIdAndSenderIdAndNotificationType(
      String recipientId, String senderId, NotificationType notificationType);
}
