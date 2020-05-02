import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";

export class DisplayAllNotificationsAction implements Action {
 public readonly type: string;

  constructor(public payload: any) {
    this.type = NotificationActionTypes.GET_ALL_NOTIFICATIONS;
  }
}
