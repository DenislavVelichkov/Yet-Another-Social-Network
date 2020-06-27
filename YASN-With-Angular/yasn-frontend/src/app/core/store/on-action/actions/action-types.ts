import {AcceptFrRequestAction} from "./accept-fr-request.action";

export const OnActionTypes = {
  PENDING_FRIEND_REQUEST: "[ON_ACTION] Pending friend request",
};

export type OnAction =
  AcceptFrRequestAction;
