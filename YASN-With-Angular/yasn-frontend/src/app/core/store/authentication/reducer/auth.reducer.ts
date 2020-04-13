import {AuthState, initialState} from "../state/auth.state";
import {Principal} from "../Principal";
import {AuthActionType, AuthActionTypes} from "../actions/auth.action.types";

export function authReducer(state: AuthState[] = [initialState],
                            action: AuthActionTypes): AuthState[] {
  switch (action.type) {
    case AuthActionType.AUTHENTICATE:
      return loginUser(state, action.payload);

    default:
      return state;
  }

  function loginUser(state: AuthState[], user: Principal): AuthState[] {
   let newState: AuthState = {
      isAuthenticated: true,
      loaded: true,
      loading: false,
      activeUser: user,
      error: null
    };

    return [...state, newState];
  }
}
