import {Action} from "@ngrx/store";
import {ProfileActionTypes} from "./action.type";
import {UserProfileState} from "../state/user-profile.state";

export class UpdateAvatarAction implements Action {
  public readonly type: string;

  constructor(public payload: UserProfileState) {
    this.type = ProfileActionTypes.UPDATE_AVATAR;
  }
}
