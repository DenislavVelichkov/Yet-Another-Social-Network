package org.yasn.data.entities;

import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.user.UserProfile;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification extends BaseEntity {

  @Column(nullable = false)
  boolean isViewed;

  @Column(nullable = false, updatable = false)
  private String senderId;

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(
      name = "recipient_id",
      referencedColumnName = "id",
      nullable = false, updatable = false)
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
  private Timestamp createdOn;

  @Column(nullable = false, updatable = false)
  private String content;
}
