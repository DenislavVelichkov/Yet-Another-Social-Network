import {Action} from "@ngrx/store";
import {OnActionTypes} from "./action-types";

export class AcceptFrRequestAction implements Action {
  readonly type: string;

  constructor(public payload: {acceptFrRequest: boolean}) {
    this.type = OnActionTypes.ACCEPT_FRIEND_REQUEST;
  }

}
