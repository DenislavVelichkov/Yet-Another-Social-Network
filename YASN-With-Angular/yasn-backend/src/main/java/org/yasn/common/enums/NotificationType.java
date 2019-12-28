package org.yasn.common.enums;

public enum NotificationType {
  MESSAGE("Message"),
  FRIEND_REQ("Friend Request");

  private final String label;

  NotificationType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

}
