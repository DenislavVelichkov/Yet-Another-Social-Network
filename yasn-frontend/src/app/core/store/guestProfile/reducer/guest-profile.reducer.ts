import {GuestProfileState, initialState} from "../state/guest-profile.state";
import {GuestProfileActionTypes} from "../actions/action.type";

export function guestProfileReducer(state: GuestProfileState = initialState,
                                    action: GuestProfileActionTypes) {

  switch (action.type) {
    case GuestProfileActionTypes.GET_DETAILS:
      return getDetails(state, action.payload);
    default:
      return state;
  }

  function getDetails(state, payload) {

    return Object.assign({}, state, payload);
  }
}
