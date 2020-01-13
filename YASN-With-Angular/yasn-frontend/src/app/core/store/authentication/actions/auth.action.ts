import {Action} from "@ngrx/store";

export const REGISTER: string = '[AUTH] Register';
export const LOGIN: string = '[AUTH] Login';
export const LOGOUT: string = '[AUTH] Logout';
export type Type = AuthAction;

export class AuthAction implements Action {
  readonly type: string;

  constructor(public payload: any) {

  }

}
