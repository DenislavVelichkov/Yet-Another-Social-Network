import {postsReducer} from "./post/reducer/posts.reducer";
import {authReducer} from "./authentication/reducer/auth.reducer";
import {userProfileReducer} from "./userProfile/reducer/user-profile.reducer";

export const appReducers = {
  posts: postsReducer,
  auth: authReducer,
  userProfile: userProfileReducer,
};
