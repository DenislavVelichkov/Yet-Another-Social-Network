import {Action} from "@ngrx/store";
import {AuthActionType} from "./auth.action.types";

export class RegisterAction implements Action {
  public readonly type: string;

  constructor(public payload?: any) {
    this.type = AuthActionType.REGISTER_USER
  }

}
