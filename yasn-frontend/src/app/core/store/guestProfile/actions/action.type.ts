import {type} from "../../../util/util";
import {GetGuestProfileDetails} from "./get-guest-profile.details";

export const GuestProfileActionTypes = {
  GET_DETAILS: type('[GUEST_PROFILE] Get Details'),

}
export type GuestProfileActionTypes =
  GetGuestProfileDetails;
