import {Action} from "@ngrx/store";
import {LoadingActionTypes} from "./action-type";

export class StartLoadingAction implements Action{
  public readonly type: string;

  constructor(public payload: boolean) {
    this.type = LoadingActionTypes.START_LOADING;
  }
}
