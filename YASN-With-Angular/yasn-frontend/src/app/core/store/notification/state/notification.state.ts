import {Notification} from "../Notification.js";

export const initialState: NotificationState = {
  allPersonalNotifications: [],
}

export interface NotificationState {
  allPersonalNotifications: Notification[];
}
