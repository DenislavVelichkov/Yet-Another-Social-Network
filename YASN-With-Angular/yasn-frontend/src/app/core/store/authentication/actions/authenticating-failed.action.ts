import {Action} from "@ngrx/store";
import {AuthActionType} from "./auth.action.types";

export class AuthenticatingFailedAction implements Action {
  public readonly type: string;

  constructor(public payload: any) {
    this.type = AuthActionType.AUTHENTICATE_ERROR;
  }

}
