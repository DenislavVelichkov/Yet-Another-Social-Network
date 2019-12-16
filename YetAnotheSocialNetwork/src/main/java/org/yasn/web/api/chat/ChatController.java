package org.yasn.web.api.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.data.models.chat.ChatMessage;
import org.yasn.web.controller.BaseController;

@Controller
public class ChatController extends BaseController {

  @GetMapping("/chat")
  public ModelAndView startToChat(ModelAndView modelAndView) {

    String username = super.getUserProfileView().getFullName();
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    modelAndView.addObject("username", username);

    return super.view("chat", modelAndView);
  }

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    return chatMessage;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  public ChatMessage addUser(@Payload ChatMessage chatMessage,
                             SimpMessageHeaderAccessor headerAccessor) {
    // Add username in web socket session
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    return chatMessage;
  }

}
