package org.java.yasn.config.websocket;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(
        "/new-post",
        "/new-friend-request",
        "/like-post",
        "/unlike-post",
        "/comment-on-post"
    );
    registry.setApplicationDestinationPrefixes("/ws");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/yasn-websocket", "/stomp")
            .setAllowedOrigins("*")
            .withSockJS();
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    super.configureWebSocketTransport(registry);
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    super.configureClientInboundChannel(registration);
  }

  @Override
  public void configureClientOutboundChannel(ChannelRegistration registration) {
    super.configureClientOutboundChannel(registration);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    super.addArgumentResolvers(argumentResolvers);
  }

  @Override
  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    super.addReturnValueHandlers(returnValueHandlers);
  }

  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    return super.configureMessageConverters(messageConverters);
  }

  @Bean
  public WebSocketHandler subProtocolWebSocketHandler() {
    return new CustomSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
  }

}
