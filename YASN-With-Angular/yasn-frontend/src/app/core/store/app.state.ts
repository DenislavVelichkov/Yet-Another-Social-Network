import {PostsState} from "./post/state/posts.state";
import {AuthState} from "./authentication/state/auth.state";

export interface AppState {
  readonly posts: PostsState;
  readonly auth: AuthState;
}
