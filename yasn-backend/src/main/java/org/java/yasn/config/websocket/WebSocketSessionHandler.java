package org.java.yasn.config.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketSessionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketSessionHandler.class);

  private volatile String currentActiveUser;

  private volatile String currentActiveService;

  private volatile Map<String, Map<String, Map<String, WebSocketSession>>> webSocketSessionsRegister;

  public WebSocketSessionHandler() {
    this.webSocketSessionsRegister = new HashMap<>();
  }

  public synchronized void register(WebSocketSession session) {
    String sessionId = session.getId();

    if (!currentActiveService.isBlank() && !currentActiveUser.isBlank()) {

      webSocketSessionsRegister
          .get(currentActiveUser)
          .get(currentActiveService)
          .putIfAbsent(sessionId, session);

      LOGGER.info("New WS session "
          + sessionId
          + " was created for "
          + currentActiveUser
          + " in service "
          + currentActiveService);
    }

  }

  public synchronized void closeAllWebSocketSessions() {

    webSocketSessionsRegister
        .values()
        .forEach(user -> user.values().forEach(serviceName -> {
          serviceName.forEach((sessionId, session) -> {
            try {
              session.close();
              serviceName.remove(sessionId);
              LOGGER.info("Session with ID: "
                  + sessionId
                  + " closed "
                  + "due inactivity"
                  + " of "
                  + user
                  + " from " + serviceName
                  + " service");
            } catch (IOException ex) {
              LOGGER.error("Error while closing web socket session: ", ex);
            }
          });
        }));
  }

  public synchronized void closeSpecificWebSocketSession(String userName, String serviceName) {

    webSocketSessionsRegister.get(userName)
                             .get(serviceName)
                             .forEach((sessionId, session) -> {

                               try {
                                 session.close();
                                 webSocketSessionsRegister.get(userName).get(serviceName).remove(sessionId, session);
                                 LOGGER.info("Session with ID: "
                                     + sessionId
                                     + " closed "
                                     + "due inactivity"
                                     + " of "
                                     + userName
                                     + " from " + serviceName
                                     + " service");
                               } catch (IOException ex) {
                                 LOGGER.error("Error while closing ws session: ", ex);
                               }

                             });
  }

  public void setCurrentActiveUser(String currentActiveUser) {
    this.currentActiveUser = currentActiveUser;
  }

  public void setCurrentActiveService(String currentActiveService) {
    this.currentActiveService = currentActiveService;
  }

  public void setWebSocketSessionsRegister(String userShortName, String serviceName) {
    webSocketSessionsRegister.putIfAbsent(userShortName, new HashMap<>());
    webSocketSessionsRegister.get(userShortName).putIfAbsent(serviceName, new ConcurrentHashMap<>());
  }

}
