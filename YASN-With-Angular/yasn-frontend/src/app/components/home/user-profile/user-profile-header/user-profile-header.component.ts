import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppState, ProfileState} from "../../../../core/store/app.state";
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {Store} from "@ngrx/store";
import {Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomSuccessSnackbarComponent} from "../../../custom-snackbar/success-snackbar/custom-success-snackbar.component";
import {AcceptFrRequestAction} from "../../../../core/store/on-action/actions/accept-fr-request.action";
import {NotificationService} from "../../../../core/services/notification/notification.service";

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
              private snackBar: MatSnackBar,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.isProfileHasPendingFrRequest$ = this.store$.select('onAction').subscribe(data => {
      this.pendingFrRequest = data.pendingFrRequest;
    })
  }


  addFriend() {
    this.notificationService.createFriendRequest(this.selectedProfileId)
  }

  acceptFriendRequest() {
  let notificationId: string;

    this.http.update(EndpointUrls.addFriend,
      {
        senderId: this.loggedInProfileId,
        recipientId: this.selectedProfileId
      }).subscribe(data => {
      notificationId = data['notificationId'];

      this.snackBar.openFromComponent(CustomSuccessSnackbarComponent, {
        duration: 4500,
        verticalPosition: "top",
        horizontalPosition: "center",
        data: "Congrats! You now have a new friend!"
      })

      this.store$.dispatch(new AcceptFrRequestAction({acceptFrRequest: true}))

    }, error => console.log(new Error(error)));
  }

  ngOnDestroy(): void {
    this.isProfileHasPendingFrRequest$.unsubscribe();
  }
}
