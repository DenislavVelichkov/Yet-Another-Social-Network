import {type} from "../../../util/util";
import {CreatePostNotificationAction} from "./create-post-notification.action";
import {DisplayAllNotificationsAction} from "./display-all-notifications.action";
import {SendFrRequestAction} from "./send-fr-request.action";


export const NotificationActionTypes = {
  GET_ALL_NOTIFICATIONS: type('[NOTIFICATIONS] Get All'),
  CREATE_POST_NOTIFICATION: type('[NOTIFICATIONS] Create Post'),
  SEND_FRIEND_REQUEST: type('[NOTIFICATIONS] Send friend request'),
};

export type NotificationActions =
  CreatePostNotificationAction
  | DisplayAllNotificationsAction
  | SendFrRequestAction;

