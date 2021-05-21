import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";

export class MarkNotificationAction implements Action{
 readonly type: string;

  constructor(public payload: {recipientId: string, notificationId: string, isRead: boolean}) {
    this.type = NotificationActionTypes.MARK_NOTIFICATION;
  }
}
