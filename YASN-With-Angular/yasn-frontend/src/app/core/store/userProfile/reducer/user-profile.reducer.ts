import {initialState, UserProfileState} from "../state/user-profile.state";
import {ProfileActionTypes} from "../actions/action.type";

export function userProfileReducer(state: UserProfileState = initialState,
                                   action: ProfileActionTypes) {

  switch (action.type) {
    case ProfileActionTypes.UPDATE_ACTIVE_PROFILE:
      return updateProfile(state, action.payload);
    default:
      return state;
  }

  function updateProfile(state, payload) {

    return Object.assign({}, state, payload);
  }
}
