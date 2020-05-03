package org.java.yasn.web.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationModel {
  private String recipientId;
  private String content;
  private String notificationType;
}
