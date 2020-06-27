import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppState, ProfileState} from "../../../../core/store/app.state";
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {Notification} from "../../../../core/store/notification/Notification";
import {Store} from "@ngrx/store";
import {Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomSnackbarComponent} from "../../../custom-snackbar/custom-snackbar.component";
import {AcceptFrRequestAction} from "../../../../core/store/on-action/actions/accept-fr-request.action";
import {DeleteNotificationAction} from "../../../../core/store/notification/actions/delete-notification.action";

@Component({
  selector: 'app-user-profile-header',
  templateUrl: './user-profile-header.component.html',
  styleUrls: ['./user-profile-header.component.css']
})
export class UserProfileHeaderComponent implements OnInit, OnDestroy {

  @Input("selectedProfileId") public selectedProfileId: string;

  @Input("loggedInProfileId") public loggedInProfileId: string;

  @Input("profileOwnerState") public profileOwnerState: ProfileState;

  @Input("isGuestProfile") public isGuestProfile: boolean = false;

  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  @Input("areTheyFriends") public areTheyFriends: boolean = false;

  private isProfileHasPendingFrRequest$: Subscription

  public pendingFrRequest: boolean;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private http: HttpRepositoryService,
              private store$: Store<AppState>,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.isProfileHasPendingFrRequest$ = this.store$.select('onAction').subscribe(data => {
      this.pendingFrRequest = data.pendingFrRequest;
    })
  }


  addFriend() {
    let activeProfileId: string = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;
    let frRequest = {
      senderId: activeProfileId,
      recipientId: this.selectedProfileId,
    };

    this.http.create<Notification>(EndpointUrls.sendFriendRequest, frRequest)
      .pipe(take(1))
      .subscribe(data => {
        this.snackBar.openFromComponent(CustomSnackbarComponent, {
          duration: 4500,
          verticalPosition: "top",
          horizontalPosition: "center",
          data: "You have send a friend request to " + data.recipientFullName
        })
      }, reason => {

        this.snackBar.openFromComponent(CustomSnackbarComponent, {
          duration: 4500,
          verticalPosition: "top",
          horizontalPosition: "center",
          data: reason.error.message
        })

        this.router.navigate([`${this.router.url}`]).catch(reason => console.log(new Error(reason)));
      });
  }

  acceptFriendRequest() {
  let notificationId: string;

    this.http.update(EndpointUrls.addFriend,
      {
        senderId: this.loggedInProfileId,
        recipientId: this.selectedProfileId
      }).subscribe(data => {
      notificationId = data['notificationId'];

      this.snackBar.openFromComponent(CustomSnackbarComponent, {
        duration: 4500,
        verticalPosition: "top",
        horizontalPosition: "center",
        data: "Congrats! You now have a new friend!"
      })

      this.store$.dispatch(new AcceptFrRequestAction({acceptFrRequest: true}))

      this.http.delete(EndpointUrls.deleteNotification + notificationId)
        .pipe(take(1))
        .subscribe(() => {
          this.store$.dispatch(new DeleteNotificationAction({recipientId: this.loggedInProfileId, notificationId: notificationId}))
        }, error => console.log(new Error(error)));

    }, error => console.log(new Error(error)));
  }

  ngOnDestroy(): void {
    this.isProfileHasPendingFrRequest$.unsubscribe();
  }
}
