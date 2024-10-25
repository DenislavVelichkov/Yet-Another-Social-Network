import {initialState, NotificationState} from "../state/notification.state";
import {NotificationActions, NotificationActionTypes} from "../actions/action.type";
import {Notification} from "../Notification";

export function notificationReducer(state: NotificationState = initialState,
                                    action: NotificationActions) {
  // Remove the enableMapSet() call as it's not defined and likely unnecessary

  switch (action.type) {
    case NotificationActionTypes.GET_ALL_NOTIFICATIONS:

      return getAllNotification(state, action.payload);

    case NotificationActionTypes.CREATE_POST_NOTIFICATION:

      return createPost(state, action.payload);

    case NotificationActionTypes.SEND_FRIEND_REQUEST:

      return sendFriendRequest(state, action.payload)

    case NotificationActionTypes.MARK_NOTIFICATION:

      return markNotification(state, action.payload);

    case NotificationActionTypes.DELETE_NOTIFICATION:

      return deleteNotification(state, action.payload);

    default:
      return state;
  }

  function getAllNotification(state: NotificationState, payload) {
    return {
      ...state,
      profileNotifications: payload
    };
  }

  function createPost(state: NotificationState, payload: { notification: Notification }) {
    return {
      ...state,
      profileNotifications: [
        ...state.profileNotifications,
        payload.notification
      ].sort((dateA: Notification, dateB: Notification) => compareDatesDesc(dateA.createdOn, dateB.createdOn))
    };
  }

  function sendFriendRequest(state: NotificationState, payload: { notification: Notification }) {
    return {
      ...state,
      profileNotifications: [
        ...state.profileNotifications,
        payload.notification
      ].sort((dateA: Notification, dateB: Notification) =>
        compareDatesDesc(dateA.createdOn, dateB.createdOn))
    };
  }

  function markNotification(state: NotificationState, payload: any) {
    return {
      ...state,
      profileNotifications: state.profileNotifications.map(notification =>
        notification.notificationId === payload.notificationId
          ? { ...notification, isViewed: payload.isRead }
          : notification
      )
    };
  }

  function deleteNotification(state: NotificationState, payload: any) {
    return {
      ...state,
      profileNotifications: state.profileNotifications
        .filter(n => n.notificationId !== payload.notificationId)
    };
  }

  function compareDatesDesc(createdOnA: Date, createdOnB: Date) {
    let a = new Date(createdOnA)
    let b = new Date(createdOnB)

    return b.getTime() - a.getTime();
  }

}
