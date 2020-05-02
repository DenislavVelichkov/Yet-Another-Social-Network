import {type} from "../../../util/util";
import {CreatePostNotificationAction} from "./create-post-notification.action";


export const NotificationActionTypes = {
  GET_ALL_NOTIFICATIONS: type('[NOTIFICATIONS] Get All'),
  CREATE_POST_NOTIFICATION: type('[NOTIFICATIONS] Create Post')
};

export type NotificationActions =
  CreatePostNotificationAction;

