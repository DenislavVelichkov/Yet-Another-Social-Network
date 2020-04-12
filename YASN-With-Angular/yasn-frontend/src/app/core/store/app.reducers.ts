import {postsReducer} from "./post/reducer/posts.reducer";
import {authReducer} from "./authentication/reducer/auth.reducer";

export const appReducers = {
  posts: postsReducer,
  auth: authReducer,
};
