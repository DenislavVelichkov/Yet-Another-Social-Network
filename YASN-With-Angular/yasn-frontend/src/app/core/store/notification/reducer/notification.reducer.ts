import {initialState, NotificationState} from "../state/notification.state";
import {NotificationActions, NotificationActionTypes} from "../actions/action.type";

export function notificationReducer(state: NotificationState = initialState,
                                    action: NotificationActions) {

  switch (action.type) {
    case NotificationActionTypes.GET_ALL_NOTIFICATIONS:

      return getAllNotification(state, action.payload);
    case NotificationActionTypes.CREATE_POST_NOTIFICATION:

      return createNotification(state, action.payload);
  }

  function getAllNotification(state: NotificationState, payload: any) {

  }

  function createNotification(state: NotificationState, payload: any) {

  }

}
