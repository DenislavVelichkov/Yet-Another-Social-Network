import {Action} from "@ngrx/store";
import {NotificationActionTypes} from "./action.type";

export class SendFrRequestAction implements Action {
  readonly type: string;

  constructor(public payload: any) {
    this.type = NotificationActionTypes.SEND_FRIEND_REQUEST;
  }
}
