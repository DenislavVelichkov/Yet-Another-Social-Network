import {PostState} from "./post/state/post.state";
import {AuthState} from "./authentication/state/auth.state";
import {UserProfileState} from "./userProfile/state/user-profile.state";

export interface AppState {
  readonly auth: AuthState;
  readonly post: PostState;
  readonly userProfile: UserProfileState;
}
