package org.yasn.data.models.view.action;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.common.enums.NotificationType;
import org.yasn.data.models.view.BaseViewModel;

@Getter
@Setter
@NoArgsConstructor
public class NotificationViewModel extends BaseViewModel {
  boolean isViewed;
  private String senderId;
  private String senderPicture;
  private String senderFullName;
  private NotificationType notificationType;
  private Timestamp createdOn;
  private String content;
}
