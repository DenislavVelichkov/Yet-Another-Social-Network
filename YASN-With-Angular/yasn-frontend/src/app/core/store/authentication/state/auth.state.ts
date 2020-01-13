import {ActiveUser} from "../ActiveUser";

export interface AuthState {
  activeUser?: ActiveUser;
  loaded: boolean;
  loading: boolean;
  error?: string;
  isAuthenticated: boolean;
}
