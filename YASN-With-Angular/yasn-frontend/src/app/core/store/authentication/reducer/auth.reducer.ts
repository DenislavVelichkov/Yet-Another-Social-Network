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
      return logout(state, action.payload)

    case AuthActionType.REGISTER_USER:
      return registerUser(state, action.payload)

    case AuthActionType.REGISTER_SUCCESS:
      return registerSuccess(state, action.payload)

    case AuthActionType.AUTHENTICATE_ERROR:
      return displayAuthError(state, action.payload)

    default:
      return state;
  }

  function storeUser(state, payload: any) {

    return Object.assign({}, state, payload);
  }

  function loginUser(state, payload: any) {

    return Object.assign({}, state, payload);
  }

  function logout(state, payload) {

    return Object.assign({}, state, payload);
  }

  function registerUser(state, payload: any) {

    return Object.assign({}, state, payload);
  }

  function registerSuccess(state, payload: any) {

    return Object.assign({}, state, payload);
  }

  function displayAuthError(state, payload: any) {

    return Object.assign({}, state, payload);
  }

}
