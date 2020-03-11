import {AuthState} from "../state/auth.state";
import {AuthActionTypes} from "../actions/auth.action.types";
import {ActionTypes} from "../actions/auth.actions";


const initialState: AuthState = {
  isAuthenticated: false,
  activeUser: null,
  loaded: false,
  loading: false,
  error: null
};

export function authReducer(state: AuthState = initialState,
                            action: AuthActionTypes) {
  switch (action.type) {
    case ActionTypes.SIGN_UP:
      break;

  }
}
