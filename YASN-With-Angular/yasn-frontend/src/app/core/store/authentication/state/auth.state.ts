import {Principal} from "../Principal";

export const initialState: AuthState = {
  activeUser: null,
  isLoggedIn: false,
  loading: false,
  error: null,
  isAuthenticated: false,
  authData: null
};

export interface AuthState {
  activeUser?: Principal;
  isLoggedIn: boolean;
  loading: boolean;
  error?: Error;
  isAuthenticated: boolean;
  authData: string;
}
