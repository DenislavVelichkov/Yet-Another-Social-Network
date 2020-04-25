import {UserAuthModel} from "../UserAuthModel";

export const initialState: AuthState = {
  activeUser: null,
  isRegistered: false,
  isLoggedIn: false,
  loading: false,
  error: null,
  isAuthenticated: false,
};

export interface AuthState {
  activeUser: UserAuthModel;
  isRegistered: boolean;
  isLoggedIn: boolean;
  loading: boolean;
  error?: Error;
  isAuthenticated: boolean;
}
