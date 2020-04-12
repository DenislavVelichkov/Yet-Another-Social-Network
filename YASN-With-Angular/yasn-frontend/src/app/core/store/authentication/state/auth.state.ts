import {Principal} from "../Principal";

export const initialState: AuthState = {
  activeUser: null,
  loaded: false,
  loading: false,
  error: null,
  isAuthenticated: false
};

export interface AuthState {
  activeUser?: Principal;
  loaded: boolean;
  loading: boolean;
  error?: string;
  isAuthenticated: boolean;
}
