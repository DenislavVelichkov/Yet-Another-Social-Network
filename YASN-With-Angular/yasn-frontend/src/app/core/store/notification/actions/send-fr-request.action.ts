import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";
import {Notification} from "../Notification";


export class SendFrRequestAction implements Action {
  readonly type: string;

  constructor(public payload: {notification: Notification}) {
    this.type = NotificationActionTypes.SEND_FRIEND_REQUEST;
  }
}
