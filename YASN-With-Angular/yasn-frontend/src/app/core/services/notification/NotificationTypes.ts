import {EndpointUrls} from "../../../shared/common/EndpointUrls";

export const NotificationsEndpointTypes = {
  sendNewFriendRequest: EndpointUrls.topicNotificationFriendRequest,
  createNewWallPost: EndpointUrls.topicCreatedNewPost,
  like: EndpointUrls.topicLike,
  unlike: EndpointUrls.topicUnLike,
  commentOnPost: EndpointUrls.topicCommentOnPost,
}
