import {type} from "../../../util/util";
import {UpdateAvatarAction} from "./update-avatar.action";

export const ProfileActionTypes = {
  UPDATE_AVATAR: type('[PROFILE] Update Avatar'),

}
export type ProfileActionTypes =
  UpdateAvatarAction;
