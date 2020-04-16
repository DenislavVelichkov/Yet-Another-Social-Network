import {PostsState} from "./post/state/posts.state";
import {AuthState} from "./authentication/state/auth.state";
import {UserProfileState} from "./userProfile/state/user-profile.state";

export interface AppState {
  readonly auth: AuthState;
  readonly posts: PostsState;
  readonly userProfile: UserProfileState;
}
