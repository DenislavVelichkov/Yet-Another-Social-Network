import {initialState, NotificationState} from "../state/notification.state";
import {NotificationActions, NotificationActionTypes} from "../actions/action.type";
import produce, {enableMapSet} from "immer";

export function notificationReducer(state: NotificationState = initialState,
                                    action: NotificationActions) {
  enableMapSet();

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

    return produce(state, draftState => {
      draftState.profileNotifications.concat(payload[1]);
    });
  }

  function createPost(state, payload) {

    return produce(state, draftState => {
      draftState.profileNotifications
        .push(payload.notification)
     /* .sort((dateA: Notification, dateB: Notification) =>
          compareDatesDesc(dateA.createdOn, dateB.createdOn));*/
    });
  }

  function sendFriendRequest(state: NotificationState, payload) {

    return produce(state, draftState => {
      draftState.profileNotifications
        .push(payload.notification);
    });
  }

  function markNotification(state: NotificationState, payload: any) {

    return produce(state, draftState => {
      draftState.profileNotifications
        .find(n => n.notificationId === payload.notificationId)
        .isViewed = payload.isRead;
    });

  }

  function deleteNotification(state: NotificationState, payload: any) {

    return produce(state, draftState => {
      draftState.profileNotifications = draftState.profileNotifications
        .filter(n => n.notificationId !== payload.notificationId);
    });

  }

  function compareDatesDesc(createdOnA: Date, createdOnB: Date) {
    let a = new Date(createdOnA)
    let b = new Date(createdOnB)

    return b.getTime() - a.getTime();
  }

}
