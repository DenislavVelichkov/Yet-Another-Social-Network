import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {Subscription, throwError} from "rxjs";
import {UpdateActiveProfileAction} from "../../../core/store/userProfile/actions/update-active-profile.action";
import {Notification} from "../../../core/store/notification/Notification";
import {timeAgoConverter} from "../../../core/util/util";
import {NotificationService} from "../../../core/services/notification/notification.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {ProfileInfoModel} from "../../../shared/models/user/ProfileInfoModel";
import {WebsocketService} from "../../../core/services/websocket/websocket.service";
import {SendFrRequestAction} from "../../../core/store/notification/actions/send-fr-request.action";
import {MatDialog} from "@angular/material/dialog";
import {SearchBarComponent} from "../../search-bar/search-bar.component";
import {PendingFrRequestAction} from "../../../core/store/on-action/actions/pending-fr-request.action";
import {take} from "rxjs/operators";
import {DeleteNotificationAction} from "../../../core/store/notification/actions/delete-notification.action";
import {MarkNotificationAction} from "../../../core/store/notification/actions/mark-notification.action";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../un-authorized-navbar/un-authorized-navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit, OnDestroy, AfterViewInit {

  activeProfileId: string;

  profilePictureUrl: string;

  userFullName: string;

  notifications: Notification[] = [];

  notificationsSubscription$: Subscription;

  notificationDropdown: boolean = false;

  constructor(private auth: AuthService,
              private store$: Store<AppState>,
              private http: HttpRepositoryService,
              private notificationService: NotificationService,
              private websocketService: WebsocketService,
              private searchDialog: MatDialog) {
  }

  ngOnInit() {
    this.activeProfileId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;

    this.websocketService.connect();

    this.http.get<ProfileInfoModel>(EndpointUrls.selectUserProfile + this.activeProfileId)
      .subscribe(value => {
        this.store$.dispatch(new UpdateActiveProfileAction(value))
      }, error => throwError(error))

    this.store$.select('userProfile').subscribe(value => {
      this.userFullName = value.userFullName;
      this.profilePictureUrl = value.avatarPictureUrl;
    })

    this.notificationService.getAllPersonalNotifications(this.activeProfileId)

    this.store$.select('notifications').subscribe(value => {
      if (value.allPersonalNotifications.get(this.activeProfileId)) {
        this.notifications = value.allPersonalNotifications.get(this.activeProfileId);
      }
    });

    this.notificationsSubscription$ = this.websocketService.getFriendRequestsData().subscribe((notification: string) => {
      let newNotification: Notification = Object.assign({}, JSON.parse(notification));

      if (newNotification.recipientId === this.activeProfileId) {
        this.store$.dispatch(new SendFrRequestAction({notification: newNotification}));
      }
    }, error => console.log(new Error(error)));

  }

  logout() {
    this.auth.logout();
  }

  convertTime(date: Date): string {
    return timeAgoConverter(date);
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
    this.notificationsSubscription$.unsubscribe();
  }

  openSearchBar() {
    const searchDialogRef = this.searchDialog.open(SearchBarComponent);
    searchDialogRef.updatePosition({top: '5rem'});
  }

  showNotificationDropdown() {
    this.notificationDropdown = !this.notificationDropdown;
  }

  sendFriendRequest(notification: Notification) {
    this.store$.dispatch(new PendingFrRequestAction({pendingFrRequest: true}))

    this.http.update(EndpointUrls.editNotification, {notificationId: notification.notificationId})
      .pipe(take(1)).subscribe(() => {
      this.store$.dispatch(new MarkNotificationAction({recipientId: notification.recipientId, notificationId: notification.notificationId, isRead: true}))
    }, error => console.log(new Error(error)));
  }

  deleteNotification(notification: Notification) {
    this.http.delete(EndpointUrls.deleteNotification + notification.notificationId)
      .pipe(take(1)).subscribe(() => {
      this.store$.dispatch(new DeleteNotificationAction(
        {
          notificationId: notification.notificationId,
          recipientId: notification.recipientId
        }));
    }, error => console.log(new Error(error)));
  }

  ngAfterViewInit(): void {

  }

}
