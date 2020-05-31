package org.java.yasn.web.controller;

import java.util.Collection;

import org.java.yasn.common.EndpointConstants;
import org.java.yasn.services.action.NotificationService;
import org.java.yasn.web.models.binding.NotificationModel;
import org.java.yasn.web.models.response.NotificationResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

  private final NotificationService notificationService;
  private final ModelMapper mapper;

  @Autowired
  public NotificationController(NotificationService notificationService,
                                ModelMapper mapper) {
    this.notificationService = notificationService;
    this.mapper = mapper;
  }

  @PostMapping(value = "/created-post", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<NotificationResponseModel> createNotificationOnPost(
      @RequestBody NotificationModel notificationModel) {

    NotificationResponseModel response =
        this.notificationService.createNotificationForNewPost(notificationModel);

    this.mapper.validate();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/get-all-notifications")
  public ResponseEntity<Collection<NotificationResponseModel>> getAllNotifications(
      @RequestBody NotificationModel notificationModel) {

    Collection<NotificationResponseModel> response =
        this.notificationService.getAllNotifications(notificationModel);

    this.mapper.validate();

    return ResponseEntity.ok(response);
  }

}
