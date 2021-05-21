import {Action} from "@ngrx/store";
import {LoadingActionTypes} from "./action-type";
import {LoadingFunction} from "../LoadingFunction";

export class StopLoadingAction implements Action {
  public readonly type: string;

  constructor(public payload: LoadingFunction) {
    this.type = LoadingActionTypes.STOP_LOADING;
  }
}

