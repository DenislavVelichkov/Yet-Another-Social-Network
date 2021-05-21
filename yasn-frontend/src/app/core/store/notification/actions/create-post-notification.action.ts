import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";
import {Notification} from "../Notification";

export class CreatePostNotificationAction implements Action {
  public readonly type: string;

  constructor(public payload: {notification: Notification}) {
    this.type = NotificationActionTypes.CREATE_POST_NOTIFICATION;
  }

}
