import {EndpointUrls} from "../../../shared/common/EndpointUrls";

export const NotificationsEndpointTypes = {
  sendNewFriendRequest: EndpointUrls.websocketNotificationFriendRequest,
  createNewWallPost: EndpointUrls.websocketTopicCreatedNewPost
}
