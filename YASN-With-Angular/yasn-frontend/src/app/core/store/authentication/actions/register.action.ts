import {Action} from "@ngrx/store";

export class RegisterAction implements Action {
  public readonly type: string;

  constructor(public payload: any) {

  }

}
