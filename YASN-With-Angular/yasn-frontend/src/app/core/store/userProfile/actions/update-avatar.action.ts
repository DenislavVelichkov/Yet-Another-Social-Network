import {Action} from "@ngrx/store";
import {ProfileActionTypes} from "./action.type";

export class UpdateAvatarAction implements Action{
  public readonly type: string;

  constructor(public payload?: any) {
    this.type = ProfileActionTypes.UPDATE_AVATAR;
  }
}
