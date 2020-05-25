import {initialState, LoadingState} from "../state/loading.state";
import {LoadingActions, LoadingActionTypes} from "../actions/action-type";

export function loadingReducer(state: LoadingState = initialState,
                               action: LoadingActions) {
  switch (action.type) {

    case LoadingActionTypes.START_LOADING:
      return startLoading(state, action.payload);
    case LoadingActionTypes.STOP_LOADING:
      return stopLoading(state, action.payload);

    default:
      return state;
  }

  function startLoading(state: LoadingState, payload: any) {
    return Object.assign({}, state, payload);
  }

  function stopLoading(state: LoadingState, payload: any) {
    return Object.assign({}, state, payload);
  }

}

