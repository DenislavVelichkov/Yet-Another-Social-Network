import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {throwError} from "rxjs";
import {UpdateActiveProfileAction} from "../../../core/store/userProfile/actions/update-active-profile.action";
import {Notification} from "../../../core/store/notification/Notification";
import {timeAgoConverter} from "../../../core/util/util";
import {NotificationService} from "../../../core/services/notification/notification.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {ProfileInfoModel} from "../../../shared/models/user/ProfileInfoModel";
import {WebsocketService} from "../../../core/services/websocket/websocket.service";
import {SendFrRequestAction} from "../../../core/store/notification/actions/send-fr-request.action";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../un-authorized-navbar/un-authorized-navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit, OnDestroy {
  profileId: string;
  profilePictureUrl: string;
  userFullName: string;
  notifications: Notification[] = [];

  constructor(private auth: AuthService,
              private store: Store<AppState>,
              private httpRepo: HttpRepositoryService,
              private notificationService: NotificationService,
              private websocketService: WebsocketService) {
  }

  ngOnInit() {
    this.profileId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;

    this.httpRepo.get<ProfileInfoModel>(EndpointUrls.selectUserProfile + this.profileId)
      .subscribe(value => {
        this.store.dispatch(new UpdateActiveProfileAction(value))
      }, error => throwError(error))

    this.store.select('userProfile').subscribe(value => {
      this.userFullName = value.userFullName;
      this.profilePictureUrl = value.avatarPictureUrl;
    })

    this.notificationService.getAllPersonalNotifications(this.profileId)

    this.store.select('notifications').subscribe(value => {
      if (value.allPersonalNotifications.get(this.profileId)) {
        this.notifications = value.allPersonalNotifications.get(this.profileId);
      }
    });

    this.websocketService.connect(EndpointUrls.websocketNotificationFriendRequest)
      .then(() => {
        this.websocketService.getFriendRequestsData().subscribe((data: string) => {
          let newNotification: Notification = Object.assign({}, JSON.parse(data));

          if (newNotification.recipientId === this.profileId) {
            this.store.dispatch(new SendFrRequestAction({notification: newNotification}));
          }

        }, error => console.log(throwError(error)));
      })
  }

  logout() {
    this.auth.logout();
  }

  convertTime(date: Date): string {
    return timeAgoConverter(date)
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
  }

}
