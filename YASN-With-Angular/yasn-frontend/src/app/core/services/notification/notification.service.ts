import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {AppState} from "../../store/app.state";
import {Store} from "@ngrx/store";
import {CreatePostNotificationAction} from "../../store/notification/actions/create-post-notification.action";
import {Notification} from "../../store/notification/Notification";
import {throwError} from "rxjs";
import {DisplayAllNotificationsAction} from "../../store/notification/actions/display-all-notifications.action";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private httpRepo: HttpRepositoryService,
              private store: Store<AppState>) {
  }

  createNotificationOnNewPost(senderId: string): void {
    this.httpRepo.create<Notification>(
      EndpointUrls.notifications + '/created-post',
      {
        senderId: senderId,
      })
      .pipe(take(1))
      .subscribe((data: Notification) => {
        this.store.dispatch(new CreatePostNotificationAction({
          allPersonalNotifications: [data],
          loading: true
        }))
      })
  }

  getAllPersonalNotifications(recipientId: string): void {
    this.httpRepo.create<Notification[]>(EndpointUrls.notifications + '/get-all-notifications', {recipientId: recipientId})
      .pipe(take(1))
      .subscribe((value: Notification[]) => {
        this.store.dispatch(new DisplayAllNotificationsAction({
          allPersonalNotifications: value
        }))
      }, error => throwError(error));
  }

}
