package org.java.yasn.web.models.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CommentResponseModel {

 private String id;

 private String senderFullName;

 private String senderAvatarPictureUrl;

 private LocalDate createdOn;

 private String picture;

 private String content;

}
