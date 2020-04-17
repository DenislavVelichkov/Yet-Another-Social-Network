import {AuthState, initialState} from "../state/auth.state";
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

    case AuthActionType.REGISTER_SUCCESS:
      return registerSuccess(state, action.payload)

    case AuthActionType.AUTHENTICATE_ERROR:
      return displayAuthError(state, action.payload)

    default:
      return state;
  }

  function storeUser(state: AuthState, payload: any) {
    const newAuthState: AuthState = {
      activeUser: payload.activeUser,
      isRegistered: payload.isRegistered,
      loading: payload.loading,
      isLoggedIn: payload.isLoggedIn,
      isAuthenticated: payload.isAuthenticated,
      error: state.error,
    };

    return newAuthState;
  }

  function loginUser(state: AuthState, payload: any) {
    const newAuthState: AuthState = {
      isLoggedIn: state.isLoggedIn,
      isRegistered: state.isRegistered,
      isAuthenticated: state.isAuthenticated,
      loading: payload.loading,
      activeUser: state.activeUser,
      error: state.error,
    };

    return newAuthState;
  }

  function logout() {
    const newAuthState: AuthState = {
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
    const newAuthState: AuthState = {
      isRegistered: payload.isRegistered,
      loading: payload.loading,
      isLoggedIn: state.isLoggedIn,
      isAuthenticated: state.isAuthenticated,
      activeUser: state.activeUser,
      error: state.error,
    };

    return newAuthState;
  }

  function registerSuccess(state: AuthState, payload: any) {
    const newAuthState: AuthState = {
      isRegistered: payload.isRegistered,
      loading: payload.loading,
      isLoggedIn: state.isLoggedIn,
      isAuthenticated: state.isAuthenticated,
      activeUser: state.activeUser,
      error: state.error,
    };

    return newAuthState;
  }

  function displayAuthError(state: AuthState, payload: any) {
    const newAuthState: AuthState = {
      isLoggedIn: state.isLoggedIn,
      isRegistered: state.isRegistered,
      isAuthenticated: state.isAuthenticated,
      loading: payload.state.loading,
      activeUser: state.activeUser,
      error: payload.error,
    };

    return newAuthState;
  }
}
