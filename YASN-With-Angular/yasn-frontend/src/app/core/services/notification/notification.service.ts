import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {AppState} from "../../store/app.state";
import {Store} from "@ngrx/store";
import {Notification} from "../../store/notification/Notification";
import {DisplayAllNotificationsAction} from "../../store/notification/actions/display-all-notifications.action";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomSuccessSnackbarComponent} from "../../../components/custom-snackbar/success-snackbar/custom-success-snackbar.component";
import {ErrorSnackbarComponent} from "../../../components/custom-snackbar/error-snackbar/error-snackbar.component";
import {Router} from "@angular/router";
import {AuthService} from "../authentication/auth.service";
import {UserProfileState} from "../../store/userProfile/state/user-profile.state";
import {Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private loggedInProfile: UserProfileState;

  private activeProfile: Subscription;

  constructor(private http: HttpRepositoryService,
              private snackBar: MatSnackBar,
              private store$: Store<AppState>,
              private router: Router,
              private auth: AuthService) {
  }

  createNotificationOnNewPost(senderId: string): void {

    this.http.create<Notification>(EndpointUrls.createPostNotification, {senderId: senderId})
      .pipe(take(1))
      .subscribe(() => {}, error => console.log(new Error(error)));
  }

  getAllPersonalNotifications(recipientId: string): void {
    this.http.create<Notification[]>(
      EndpointUrls.getAllNotificationsForLoggedInUser,
      {recipientId: recipientId})
      .pipe(take(1))
      .subscribe((value: Notification[]) => {
        let allNotifications = [recipientId, value];
        this.store$.dispatch(new DisplayAllNotificationsAction(allNotifications));
      }, error => console.log(new Error(error)));
  }

  createFriendRequest(selectedProfileId: string) {
    let activeProfileId: string = this.auth.getActiveUser().userProfileId;
    let frRequest = {
      senderId: activeProfileId,
      recipientId: selectedProfileId,
    };

    this.http.create<Notification>(EndpointUrls.sendFriendRequest, frRequest)
      .pipe(take(1))
      .subscribe(data => {
        this.snackBar.openFromComponent(CustomSuccessSnackbarComponent, {
          duration: 4500,
          verticalPosition: "top",
          horizontalPosition: "center",
          data: "You have send a friend request to " + data.recipientFullName
        })
      }, reason => {

        this.snackBar.openFromComponent(ErrorSnackbarComponent, {
          duration: 4500,
          verticalPosition: "top",
          horizontalPosition: "center",
          data: reason.error.message
        })

        this.router.navigate([`${this.router.url}`]).catch(reason => console.log(new Error(reason)));
      });
  }
}
