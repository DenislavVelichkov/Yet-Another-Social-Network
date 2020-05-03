package org.java.yasn.repository.action;

import java.util.Collection;

import org.java.yasn.data.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

  Collection<Notification> findByRecipientId(String recipientId);

}
