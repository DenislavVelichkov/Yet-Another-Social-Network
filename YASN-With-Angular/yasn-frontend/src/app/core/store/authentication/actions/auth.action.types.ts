import {RegisterAction} from "./register.action";
import {AuthenticateAction} from "./authenticate.action";
import {type} from "../../../util/util";

export const AuthActionType = {
  AUTHENTICATE: type('[AUTH] Authenticate'),
  AUTHENTICATE_ERROR: type('[AUTH] Authentication error'),
  AUTHENTICATE_SUCCESS: type('[AUTH] Authentication success'),
  AUTHENTICATED: type('[AUTH] Authenticated'),
  AUTHENTICATED_ERROR: type('[AUTH] Authenticated error'),
  AUTHENTICATED_SUCCESS: type('[AUTH] Authenticated success'),
};

export type AuthActionTypes =
  RegisterAction
  | AuthenticateAction;
