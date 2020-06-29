package org.java.yasn.data.entities;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.user.UserProfile;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "notifications")
public class Notification extends BaseEntity {

  @Column(nullable = false)
  boolean isViewed;

  @ManyToOne(targetEntity = UserProfile.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "sender_id",
      referencedColumnName = "id",
      nullable = false, updatable = false)
  private UserProfile sender;

  @ManyToOne(targetEntity = UserProfile.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "recipient_id",
      referencedColumnName = "id",
      nullable = true, updatable = false)
  private UserProfile recipient;

  @Column(nullable = false)
  private String senderPicture;

  @Column(nullable = false, updatable = false)
  private String senderFullName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private NotificationType notificationType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on",
      updatable = false,
      nullable = false)
  private LocalDateTime createdOn;

  @Column(nullable = false, updatable = false)
  private String content;
}

