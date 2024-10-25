import {PostState} from "./post/state/post.state";
import {AuthState} from "./authentication/state/auth.state";
import {UserProfileState} from "./userProfile/state/user-profile.state";
import {NotificationState} from "./notification/state/notification.state";
import {LoadingState} from "./loading/state/loading.state";
import {GuestProfileState} from "./guestProfile/state/guest-profile.state";
import {OnActionState} from "./on-action/state/on-action.state";

export type ProfileState = UserProfileState | GuestProfileState;

export interface AppState {
  readonly auth: AuthState;
  readonly newsFeed: PostState;
  readonly userProfile: UserProfileState;
  readonly guestProfile: GuestProfileState;
  readonly notifications: NotificationState;
  readonly loadingFunction: LoadingState;
  readonly onAction: OnActionState;
}
