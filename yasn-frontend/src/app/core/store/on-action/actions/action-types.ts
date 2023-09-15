import {PendingFrRequestAction} from "./pending-fr-request.action";
import {AcceptFrRequestAction} from "./accept-fr-request.action";

export const OnActionTypes = {
  PENDING_FRIEND_REQUEST: "[ON_ACTION] Pending friend request",
  ACCEPT_FRIEND_REQUEST: "[ON_ACTION] Accept friend request",
};

export type OnAction =
  PendingFrRequestAction
  | AcceptFrRequestAction;
