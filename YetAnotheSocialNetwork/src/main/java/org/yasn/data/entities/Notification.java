package org.yasn.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.entities.user.UserProfile;

import javax.persistence.*;

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
}
