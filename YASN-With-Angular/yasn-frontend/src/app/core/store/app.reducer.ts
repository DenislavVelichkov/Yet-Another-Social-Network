import {postsReducer} from "./post/reducer/posts.reducer";
import {authReducer} from "./authentication/reducer/auth.reducer";
import {userProfileReducer} from "./userProfile/reducer/user-profile.reducer";
import {notificationReducer} from "./notification/reducer/notification.reducer";

export const appReducer = {
  newsFeed: postsReducer,
  auth: authReducer,
  userProfile: userProfileReducer,
  notifications: notificationReducer,
};
