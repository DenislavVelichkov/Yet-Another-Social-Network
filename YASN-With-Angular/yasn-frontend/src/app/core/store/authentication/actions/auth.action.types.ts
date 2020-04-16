import {RegisterAction} from "./register.action";
import {AuthenticatingAction} from "./authenticatingAction";
import {type} from "../../../util/util";
import {AuthenticatedAction} from "./authenticated.action";
import {LogoutAction} from "./logout.action";

export const AuthActionType = {
  AUTHENTICATE: type('[AUTH] Authenticate'),
  AUTHENTICATE_ERROR: type('[AUTH] Authentication error'),
  AUTHENTICATE_SUCCESS: type('[AUTH] Authentication success'),
  AUTHENTICATED: type('[AUTH] Authenticated'),
  AUTHENTICATED_ERROR: type('[AUTH] Authenticated error'),
  AUTHENTICATED_SUCCESS: type('[AUTH] Authenticated success'),
  LOGOUT_SUCCESS: type('[AUTH] Logout success'),
  REGISTER_USER: type('[AUTH] Registration...')

};

export type AuthActionTypes =
  RegisterAction
  | AuthenticatingAction
  | AuthenticatedAction
  | LogoutAction;
