import {Action} from "@ngrx/store";
import {AuthActionType} from "./auth.action.types";

export class LogoutAction implements Action{
  public readonly type: string;

  constructor(public payload: any) {
    this.type = AuthActionType.LOGOUT_SUCCESS;
  }

}
