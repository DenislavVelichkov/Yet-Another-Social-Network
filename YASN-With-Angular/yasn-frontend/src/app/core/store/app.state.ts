import {PostState} from "./post/state/post.state";
import {AuthState} from "./authentication/state/auth.state";
import {UserProfileState} from "./userProfile/state/user-profile.state";
import {NotificationState} from "./notification/state/notification.state";

export interface AppState {
  readonly auth: AuthState;
  readonly newsFeed: PostState;
  readonly userProfile: UserProfileState;
  readonly notifications: NotificationState;
}
