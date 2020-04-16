import {initialState, UserProfileState} from "../state/user-profile.state";
import {ProfileActionTypes} from "../actions/action.type";
import {AvatarModel} from "../AvatarModel";

export function userProfileReducer(state: UserProfileState = initialState,
                                   action: ProfileActionTypes) {

  switch (action.type) {
    case ProfileActionTypes.UPDATE_AVATAR:
      return updateAvatar(state, action.payload);
    default:
      return state;
  }

  function updateAvatar(state: UserProfileState, payload: any) {
    const newAvatarState: AvatarModel = {
      userFullName: payload.fullName,
      avatarPictureUrl: payload.profileAvatarPicture,
      coverPictureUrl: payload.profileCoverPicture,
    }
    return newAvatarState;
  }
}
