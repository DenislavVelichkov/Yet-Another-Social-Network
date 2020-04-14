import {Action} from "@ngrx/store";
import {AuthActionType} from "./auth.action.types";

export class AuthenticatedAction implements Action{
  public readonly type: string;

  constructor(public payload: any) {
    this.type = AuthActionType.AUTHENTICATE_SUCCESS;
  }

}
