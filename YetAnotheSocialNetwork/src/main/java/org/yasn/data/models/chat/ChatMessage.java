package org.yasn.data.models.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessage {
  private MessageType type;
  private String content;
  private String avatarPictureURL;
  private String sender;

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
  }
}
