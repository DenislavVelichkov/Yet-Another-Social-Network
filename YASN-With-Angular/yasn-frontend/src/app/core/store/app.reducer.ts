import {postsReducer} from "./post/reducer/posts.reducer";
import {authReducer} from "./authentication/reducer/auth.reducer";
import {userProfileReducer} from "./userProfile/reducer/user-profile.reducer";
import {notificationReducer} from "./notification/reducer/notification.reducer";
import {loadingReducer} from "./loading/reducer/loading.reducer";
import {guestProfileReducer} from "./guestProfile/reducer/guest-profile.reducer";
import {onAppActionReducer} from "./on-action/reducer/on-action.reducer";

export const appReducer = {
  newsFeed: postsReducer,
  auth: authReducer,
  userProfile: userProfileReducer,
  guestProfile: guestProfileReducer,
  notifications: notificationReducer,
  loadingFunction: loadingReducer,
  onAction: onAppActionReducer,
};
