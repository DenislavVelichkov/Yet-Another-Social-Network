import {AuthState, initialState} from "../state/auth.state";
import {AuthActionType, AuthActionTypes} from "../actions/auth.action.types";
import {UserAuthModel} from "../UserAuthModel";

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

    case AuthActionType.REGISTER_SUCCESS:
      return registerSuccess(state, action.payload)

    default:
      return state;
  }

  function storeUser(state: AuthState, payload: UserAuthModel) {
    let newAuthState: AuthState = {
      isLoggedIn: true,
      isRegistered: false,
      isAuthenticated: true,
      loading: false,
      activeUser: payload,
      error: null,
    };

    return newAuthState;
  }

  function loginUser(state: AuthState, payload: any) {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isRegistered: false,
      isAuthenticated: false,
      loading: true,
      activeUser: null,
      error: null,
    };

    return newAuthState;
  }

  function logout() {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isRegistered: false,
      isAuthenticated: false,
      loading: false,
      activeUser: null,
      error: null,
    };

    return newAuthState;
  }

  function registerUser(state: AuthState, payload: any) {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isRegistered: payload.isRegistered,
      isAuthenticated: false,
      loading: payload.loading,
      activeUser: null,
      error: null,
    };

    return newAuthState;
  }

  function registerSuccess(state: AuthState, payload: any) {
    let newAuthState: AuthState = {
      isLoggedIn: false,
      isRegistered: payload.isRegistered,
      isAuthenticated: false,
      loading: payload.loading,
      activeUser: null,
      error: null,
    };

    return newAuthState;
  }
}
