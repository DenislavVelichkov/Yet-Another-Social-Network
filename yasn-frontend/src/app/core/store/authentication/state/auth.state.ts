import {UserAuthModel} from "../UserAuthModel";

export const initialState: AuthState = {
  activeUser: null,
  isRegistered: false,
  isLoggedIn: false,
  error: null,
  isAuthenticated: false,
};

export interface AuthState {
  activeUser: UserAuthModel;
  isRegistered: boolean;
  isLoggedIn: boolean;
  error?: Error;
  isAuthenticated: boolean;
}
