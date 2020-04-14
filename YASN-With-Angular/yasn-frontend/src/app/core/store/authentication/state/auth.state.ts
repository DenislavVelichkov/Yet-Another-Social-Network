import {Principal} from "../Principal";

export const initialState: AuthState = {
  activeUser: null,
  loading: false,
  error: null,
  isAuthenticated: false,
  authData: null
};

export interface AuthState {
  activeUser?: Principal;
  loading: boolean;
  error?: Error;
  isAuthenticated: boolean;
  authData: string;
}
