import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {throwError} from "rxjs";
import {UpdateAvatarAction} from "../../../core/store/userProfile/actions/update-avatar.action";
import {Notification} from "../../../core/store/notification/Notification";
import {timeAgoConverter} from "../../../core/util/util";
import {NotificationService} from "../../../core/services/notification/notification.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit {
  private profilePictureUrl: string;
  private profileId: string;
  private userFullName: string;
  private notifications: Notification[];

  constructor(private auth: AuthService,
              private store: Store<AppState>,
              private httpRepo: HttpRepositoryService,
              private notificationService: NotificationService) {
  }

  ngOnInit() {

    this.store.select('auth').subscribe(value => {
      this.profileId = value.activeUser.userProfileId;
    })

    this.httpRepo.get(EndpointUrls.selectUserProfile + this.profileId)
      .subscribe(value => {
      this.store.dispatch(new UpdateAvatarAction(value))
    }, error => throwError(error))

    this.store.select('userProfile').subscribe(value => {
      this.userFullName = value.userFullName;
      this.profilePictureUrl = value.avatarPictureUrl;
    })

    this.notificationService.getAllPersonalNotifications(this.profileId)

    this.store.select('notifications').subscribe(value => {
      this.notifications = value.allPersonalNotifications;
    });

  }

  logout() {
    this.auth.logout();
  }

  convertTime(date: Date): string {
    return timeAgoConverter(date)
  }

}
