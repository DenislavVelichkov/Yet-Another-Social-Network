import {AuthState, initialState} from "../state/auth.state";
import {Principal} from "../Principal";
import {AuthActionType, AuthActionTypes} from "../actions/auth.action.types";

export function authReducer(state: AuthState = initialState,
                            action: AuthActionTypes) {

  switch (action.type) {
    case AuthActionType.AUTHENTICATE:
      return loginUser(state, action.payload);

    case AuthActionType.AUTHENTICATE_SUCCESS:
      return storeUser(state, action.payload)

    case AuthActionType.LOGOUT_SUCCESS:
      return logout()

    case AuthActionType.REGISTER_USER:
      return registerUser(state, action.payload)

    default:
      return state;
  }

  function storeUser(state: AuthState, payload: Principal) {
    let newAuthState: AuthState = {
      isLoggedIn: true,
      isAuthenticated: true,
      loading: false,
      activeUser: payload,
      error: null,
      authData: payload._token
    };

    return newAuthState;
  }

  function loginUser(state: AuthState, payload: any) {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isAuthenticated: false,
      loading: true,
      activeUser: null,
      error: null,
      authData: payload
    };

    return newAuthState;
  }

  function logout() {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isAuthenticated: false,
      loading: false,
      activeUser: null,
      error: null,
      authData: null
    };

    return newAuthState;
  }

  function registerUser(state: AuthState, payload: any) {

    let newAuthState: AuthState = {
      isLoggedIn: false,
      isAuthenticated: false,
      loading: true,
      activeUser: null,
      error: null,
      authData: null
    };

    return newAuthState;
  }
}
