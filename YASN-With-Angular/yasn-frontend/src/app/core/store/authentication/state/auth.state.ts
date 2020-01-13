import {Principal} from "../Principal";

const initialState: AuthState = {
  isAuthenticated: false,
  loaded: false,
  loading: false
};

export interface AuthState {
  activeUser?: Principal;
  loaded: boolean;
  loading: boolean;
  error?: string;
  isAuthenticated: boolean;
}
