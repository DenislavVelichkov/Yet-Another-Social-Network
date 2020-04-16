import {Principal} from "../Principal";

export const initialState: AuthState = {
  activeUser: null,
  isRegistered: false,
  isLoggedIn: false,
  loading: false,
  error: null,
  isAuthenticated: false,
  authData: null
};

export interface AuthState {
  activeUser?: Principal;
  isRegistered: boolean;
  isLoggedIn: boolean;
  loading: boolean;
  error?: Error;
  isAuthenticated: boolean;
  authData: string;
}
