import {Notification} from "../Notification";

export const initialState: NotificationState = {
  profileNotifications: new Array<Notification>(),
}

export interface NotificationState {
  profileNotifications: Array<Notification>;
}

