package org.java.yasn.web.controller.websocket;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

@Controller
@RequestMapping("http://localhost:8000/stats")
@AllArgsConstructor
public class WebSocketStatsController {

  private final WebSocketMessageBrokerStats stats;

  @GetMapping("/getStats")
  public String getWebSocketStats() {
    return stats.toString();
  }

}
