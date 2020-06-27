import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";

export class DeleteNotificationAction implements Action {
 readonly type: string;

  constructor(public payload: {recipientId: string, notificationId: string}) {
    this.type = NotificationActionTypes.DELETE_NOTIFICATION;
  }
}
