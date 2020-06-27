import {Action} from "@ngrx/store";
import {OnActionTypes} from "./action-types";

export class AcceptFrRequestAction implements Action {
  readonly type: string;

  constructor(public payload: { pendingFrRequest: boolean }) {
    this.type = OnActionTypes.PENDING_FRIEND_REQUEST;
  }

}
