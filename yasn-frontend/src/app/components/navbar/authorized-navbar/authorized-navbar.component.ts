import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {Subscription} from "rxjs";
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
import {CreatePostNotificationAction} from "../../../core/store/notification/actions/create-post-notification.action";
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../un-authorized-navbar/un-authorized-navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit, OnDestroy, AfterViewInit {

  loggedInProfile: UserProfileState;

  activeProfileId: string;

  profilePictureUrl: string;

  userFullName: string;

  notifications: Array<Notification>;

  friendReqNotificationsSub$: Subscription;

  newPostNotificationsSub$: Subscription;

  notificationDropdown: boolean = false;

  notificationSenderFriends: Array<string>;

  constructor(private auth: AuthService,
              private store$: Store<AppState>,
              private http: HttpRepositoryService,
              private notificationService: NotificationService,
              private websocketService: WebsocketService,
              private searchDialog: MatDialog) {
    this.notifications = new Array<Notification>();
  }

  ngOnInit() {
    this.activeProfileId = this.auth.getActiveUser().userProfileId;
    this.store$.select('userProfile').subscribe(value => this.loggedInProfile == value);
    this.websocketService.connect();

    this.http.get<ProfileInfoModel>(EndpointUrls.selectUserProfile + this.activeProfileId)
      .subscribe(value => {
        this.store$.dispatch(new UpdateActiveProfileAction(value))
      }, error => new Error(error))

    this.store$.select('userProfile').subscribe(value => {
      this.userFullName = value.userFullName;
      this.profilePictureUrl = value.avatarPictureUrl;
    })

    this.notificationService.getAllPersonalNotifications(this.activeProfileId)

    this.store$.select('notifications').subscribe(value => {
        this.notifications = value.profileNotifications;
    });

    this.friendReqNotificationsSub$ = this.websocketService.getFriendRequestsData().subscribe((notification: string) => {
      let newNotification: Notification = Object.assign({}, JSON.parse(notification));

      if (newNotification.recipientId === this.activeProfileId) {
        this.store$.dispatch(new SendFrRequestAction({notification: newNotification}));
      }
    }, error => console.log(new Error(error)));

    this.newPostNotificationsSub$ = this.websocketService.getPostNotificationData().subscribe(async (notification: string) => {
      let newNotification: Notification = Object.assign({}, JSON.parse(notification));

      this.store$.dispatch(new CreatePostNotificationAction({notification: newNotification}));
    })
  }

  logout() {
    this.auth.logout();
  }

  convertTime(date: Date): string {
    return timeAgoConverter(date);
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
    this.friendReqNotificationsSub$.unsubscribe();
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
        }));
    }, error => console.log(new Error(error)));
  }

  ngAfterViewInit(): void {

  }

}
