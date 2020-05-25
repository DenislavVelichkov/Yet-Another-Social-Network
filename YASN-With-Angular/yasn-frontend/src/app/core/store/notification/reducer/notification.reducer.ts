import {initialState, NotificationState} from "../state/notification.state";
import {NotificationActions, NotificationActionTypes} from "../actions/action.type";
import {Notification} from "../Notification";

export function notificationReducer(state: NotificationState = initialState,
                                    action: NotificationActions) {

  switch (action.type) {
    case NotificationActionTypes.GET_ALL_NOTIFICATIONS:

      return getAllNotification(state, action.payload);

    case NotificationActionTypes.CREATE_POST_NOTIFICATION:

      return createNotification(state, action.payload);

    default:
      return state;
  }

  function getAllNotification(state, payload) {

    return Object.assign({}, state, payload);
  }

  function createNotification(state, payload) {
    let newNotification: NotificationState = {
      allPersonalNotifications: state.allPersonalNotifications
        .concat(payload.allPersonalNotifications)
        .sort((dateA: Notification, dateB: Notification) =>
          compareDatesDesc(dateA.createdOn, dateB.createdOn)),
    }

    return newNotification;
  }


  function compareDatesDesc(createdOnA: Date, createdOnB: Date) {
    let a = new Date(createdOnA)
    let b = new Date(createdOnB)

    return b.getTime() - a.getTime();
  }

}
