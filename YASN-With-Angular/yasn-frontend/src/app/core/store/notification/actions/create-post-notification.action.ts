import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";

export class CreatePostNotificationAction implements Action{
  public readonly type: string;

  constructor(public payload: any) {
    this.type = NotificationActionTypes.CREATE_POST_NOTIFICATION;
  }

}
