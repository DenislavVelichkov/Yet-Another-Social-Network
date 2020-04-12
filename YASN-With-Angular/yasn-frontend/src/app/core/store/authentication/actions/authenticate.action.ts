import {Action} from "@ngrx/store";
import {AuthActionType} from "./auth.action.types";
import {Principal} from "../Principal";

export class AuthenticateAction implements Action{
  public readonly type: string;

  constructor(public payload: Principal) {
    this.type = AuthActionType.AUTHENTICATE
  }

}
