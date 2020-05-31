package org.java.yasn.common.enums;

import java.util.Arrays;

public enum NotificationType {
  MESSAGE("message"),
  CREATED_A_POST("created a post"),
  COMMENT_ON_POST("commented on post"),
  FRIEND_REQ("add friend request");

  private final String label;

  NotificationType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

  public static NotificationType getNotificationType(String label) {

    return Arrays.stream(NotificationType.values())
             .filter(notificationType -> notificationType.getLabel().equals(label))
             .findAny()
             .orElseThrow(() -> new IllegalArgumentException(
              String.format("There is no notification type of %s", label)));
  }

}
