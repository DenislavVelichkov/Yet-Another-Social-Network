import {RegisterAction} from "./register.action";
import {AuthenticatingAction} from "./authenticating.action";

import {AuthenticatedAction} from "./authenticated.action";
import {LogoutAction} from "./logout.action";
import {RegisterSuccessAction} from "./register-success.action";
import {AuthenticatingFailedAction} from "./authenticating-failed.action";
import {type} from "../../../util/util";

export const AuthActionType = {
  AUTHENTICATE: type('[AUTH] Authenticate'),
  AUTHENTICATE_ERROR: type('[AUTH] Authentication error'),
  AUTHENTICATE_SUCCESS: type('[AUTH] Authentication success'),
  AUTHENTICATED: type('[AUTH] Authenticated'),
  AUTHENTICATED_ERROR: type('[AUTH] Authenticated error'),
  AUTHENTICATED_SUCCESS: type('[AUTH] Authenticated success'),
  LOGOUT_SUCCESS: type('[AUTH] Logout success'),
  REGISTER_USER: type('[AUTH] Registration...'),
  REGISTER_SUCCESS: type('[AUTH] Successful registration...')

};

export type AuthActionTypes =
  RegisterAction
  | RegisterSuccessAction
  | AuthenticatingAction
  | AuthenticatedAction
  | LogoutAction
  | AuthenticatingFailedAction;
