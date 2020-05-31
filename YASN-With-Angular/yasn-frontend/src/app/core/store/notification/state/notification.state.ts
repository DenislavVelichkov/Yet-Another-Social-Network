import {Notification} from "../Notification.js";

export const initialState: NotificationState = {
  allPersonalNotifications: new Map<string, Notification[]>([])
};

export interface NotificationState {
  allPersonalNotifications: Map<string, Notification[]>;
}
