package org.java.yasn.web.models.response;

import java.sql.Timestamp;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseModel {

 private String id;

 private String commentOwnerProfileId;

 private String wallPostId;

 private String avatarPictureUrl;

 private String senderFullName;

 private Timestamp createdOn;

 private Collection<String> pictures;

 private String content;

}
