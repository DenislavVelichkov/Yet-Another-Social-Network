import {AuthState} from "../state/auth.state";
import {AuthenticateAction} from "../actions/authenticate.action";

const initialState: AuthState = {
  isAuthenticated: false,
  activeUser: null,
  loaded: false,
  loading: false,
  error: null
};

export function authReducer(state: AuthState = initialState,
                            action: AuthenticateAction) {

}
