import {type} from "../../../util/util";
import {UpdateActiveProfileAction} from "./update-active-profile.action";

export const ProfileActionTypes = {
  UPDATE_ACTIVE_PROFILE: type('[ACTIVE_PROFILE] Update Profile'),

}
export type ProfileActionTypes =
  UpdateActiveProfileAction;
