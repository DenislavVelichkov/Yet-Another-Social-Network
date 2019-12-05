package org.yasn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, String> {

  List<Notification> findAllByRecipientIdAndSenderId(String recipientId, String senderId);

  Optional<Notification> findByRecipientIdAndSenderIdAndNotificationType(String recipientId, String senderId, NotificationType notificationType);

}
