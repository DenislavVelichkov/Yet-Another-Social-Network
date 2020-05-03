package org.java.yasn.web.models.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.NotificationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseModel {

  private boolean isViewed;

  private String senderPicture;

  private String senderFullName;

  private NotificationType notificationType;

  private LocalDate createdOn;

  private String content;

}
