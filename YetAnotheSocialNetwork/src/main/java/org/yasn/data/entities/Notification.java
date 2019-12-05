package org.yasn.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.user.UserProfile;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification extends BaseEntity {

  private String senderId;

  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "recipient_id", referencedColumnName = "id")
  private UserProfile recipient;

  private String senderPicture;

  private String senderFullName;

  boolean isViewed;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on")
  private Timestamp createdOn;

  private String content;
}
