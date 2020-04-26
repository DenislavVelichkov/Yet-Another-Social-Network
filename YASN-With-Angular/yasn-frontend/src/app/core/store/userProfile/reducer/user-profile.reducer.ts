import {initialState, UserProfileState} from "../state/user-profile.state";
import {ProfileActionTypes} from "../actions/action.type";

export function userProfileReducer(state: UserProfileState = initialState,
                                   action: ProfileActionTypes) {

  switch (action.type) {
    case ProfileActionTypes.UPDATE_AVATAR:
      return updateAvatar(state, action.payload);
    default:
      return state;
  }

  function updateAvatar(state, payload) {

    return Object.assign({}, state, payload);
  }
}
