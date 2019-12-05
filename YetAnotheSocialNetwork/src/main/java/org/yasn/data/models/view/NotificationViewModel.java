package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class NotificationViewModel extends BaseViewModel {
  private String senderId;
  private String senderPicture;
  private String senderFullName;
  boolean isViewed;
  private NotificationType notificationType;
  private Timestamp createdOn;
  private String content;
}
