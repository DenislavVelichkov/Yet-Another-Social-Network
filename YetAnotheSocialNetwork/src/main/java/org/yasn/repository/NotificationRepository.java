package org.yasn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasn.data.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
  List<Notification> findAllByRecipientId(String id);

  List<Notification> findAllByRecipientIdAndSenderId(String recipientId, String senderId);
}
