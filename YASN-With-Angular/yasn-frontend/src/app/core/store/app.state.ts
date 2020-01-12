import {PostsState} from "./post/state/posts.state";

export interface AppState {
  readonly posts: PostsState;
}
