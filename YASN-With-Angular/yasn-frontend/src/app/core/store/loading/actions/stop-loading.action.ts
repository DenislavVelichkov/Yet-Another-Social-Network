import {Action} from "@ngrx/store";
import {LoadingActionTypes} from "./action-type";

export class StopLoadingAction implements Action{
  public readonly type: string;

  constructor(public payload: boolean) {
    this.type = LoadingActionTypes.STOP_LOADING;
  }
}

