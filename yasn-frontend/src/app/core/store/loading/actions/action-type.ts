import {type} from "../../../util/util";
import {StartLoadingAction} from "./start-loading.action";
import {StopLoadingAction} from "./stop-loading.action";

export const LoadingActionTypes = {
  START_LOADING: type('[LOADING] Start loading'),
  STOP_LOADING: type('[LOADING] Stop loading'),
}

export type LoadingActionsTypes = StartLoadingAction | StopLoadingAction;
