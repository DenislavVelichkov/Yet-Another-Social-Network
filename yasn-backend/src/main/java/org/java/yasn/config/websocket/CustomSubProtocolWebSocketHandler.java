package org.java.yasn.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

public class CustomSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler  {

  @Autowired
  private WebSocketSessionHandler webSocketSessionHandler;
  /**
   * Create a new {@code SubProtocolWebSocketHandler} for the given inbound and outbound channels.
   *  @param clientInboundChannel  the inbound {@code MessageChannel}
   *  @param clientOutboundChannel the outbound {@code MessageChannel}
   */
  public CustomSubProtocolWebSocketHandler(MessageChannel clientInboundChannel,
                                           SubscribableChannel clientOutboundChannel) {
    super(clientInboundChannel, clientOutboundChannel);

  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    webSocketSessionHandler.register(session);
    super.afterConnectionEstablished(session);
  }
}
