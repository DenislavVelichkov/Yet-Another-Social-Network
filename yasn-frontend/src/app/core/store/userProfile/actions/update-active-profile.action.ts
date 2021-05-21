import {Action} from "@ngrx/store";
import {ProfileActionTypes} from "./action.type";
import {ProfileInfoModel} from "../../../../shared/models/user/ProfileInfoModel";

export class UpdateActiveProfileAction implements Action {
  public readonly type: string;

  constructor(public payload: ProfileInfoModel) {
    this.type = ProfileActionTypes.UPDATE_ACTIVE_PROFILE;
  }
}
