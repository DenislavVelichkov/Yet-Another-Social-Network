import {Action} from "@ngrx/store";
import {GuestProfileActionTypes} from "./action.type";
import {GuestProfileState} from "../state/guest-profile.state";

export class GetGuestProfileDetails implements Action {
  public readonly type: string;

  constructor(public payload: GuestProfileState) {
    this.type = GuestProfileActionTypes.GET_DETAILS;
  }
}
