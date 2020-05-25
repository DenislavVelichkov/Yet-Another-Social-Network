import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";
import {Notification} from "../Notification";

export class DisplayAllNotificationsAction implements Action {
 public readonly type: string;

  constructor(public payload: {allPersonalNotifications: Notification[]}) {
    this.type = NotificationActionTypes.GET_ALL_NOTIFICATIONS;
  }
}
