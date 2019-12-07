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

  boolean isViewed;
  private String senderId;
  @ManyToOne(targetEntity = UserProfile.class)
  @JoinColumn(name = "recipient_id", referencedColumnName = "id")
  private UserProfile recipient;
  private String senderPicture;
  private String senderFullName;
  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on")
  private Timestamp createdOn;

  private String content;
}
