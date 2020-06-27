import {initialState, OnActionState} from "../state/on-action.state";
import {OnAction, OnActionTypes} from "../actions/action-types";

export function onAppActionReducer(state: OnActionState = initialState,
                                   action: OnAction) {
  switch (action.type) {
    case OnActionTypes.PENDING_FRIEND_REQUEST:
      return reviewFriendRequest(state, action);

    case OnActionTypes.ACCEPT_FRIEND_REQUEST:
      return acceptFriendRequest(state, action);
    default:
       return state;
  }
}

function reviewFriendRequest(state: OnActionState, action: OnAction) {
  return Object.assign({}, state, action.payload);
}


function acceptFriendRequest(state: OnActionState, action: OnAction) {
  return Object.assign({}, state, action.payload);
}
