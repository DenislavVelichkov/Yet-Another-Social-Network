export const initialState: NotificationState = {
  allPersonalNotifications: [],
  loading: false
}

export interface NotificationState {
  allPersonalNotifications: Notification[];
  loading: boolean;
}
