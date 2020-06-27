package org.java.yasn.web.controller;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.services.action.NotificationService;
import org.java.yasn.web.models.binding.ActionModel;
import org.java.yasn.web.models.response.NotificationResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final ModelMapper mapper;
  private final SimpMessageSendingOperations sendNotification;

  @PostMapping(value = "/created-post", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<NotificationResponseModel> createNotificationOnPost(
      @RequestBody ActionModel actionModel) {

    NotificationResponseModel response =
        this.notificationService.createNotificationForNewPost(actionModel);

    this.mapper.validate();

    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/send-friend-request", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<NotificationResponseModel> sendFriendRequest(@RequestBody ActionModel notification) {

    NotificationResponseModel response = this.notificationService.createFriendRequest(notification);
    this.mapper.validate();
    sendNotification.convertAndSend("/new-friend-request", response);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/get-all-notifications")
  public ResponseEntity<Collection<NotificationResponseModel>> getAllNotifications(
      @RequestBody ActionModel actionModel) {

    Collection<NotificationResponseModel> response =
        this.notificationService.getAllNotifications(actionModel);
    this.mapper.validate();

    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "/delete-notification/{notificationId}")
  public ResponseEntity<?> deleteNotification(
      @PathVariable(name = "notificationId") String notificationId) {

    this.notificationService.deleteNotification(notificationId);

    return ResponseEntity.ok().build();
  }

}
