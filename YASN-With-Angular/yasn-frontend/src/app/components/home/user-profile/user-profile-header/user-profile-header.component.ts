import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppState, ProfileState} from "../../../../core/store/app.state";
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {Notification} from "../../../../core/store/notification/Notification";
import {Store} from "@ngrx/store";
import {Subscription, throwError} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomSnackbarComponent} from "../../../custom-snackbar/custom-snackbar.component";

@Component({
  selector: 'app-user-profile-header',
  templateUrl: './user-profile-header.component.html',
  styleUrls: ['./user-profile-header.component.css']
})
export class UserProfileHeaderComponent implements OnInit, OnDestroy {

  @Input("selectedProfileId") selectedProfileId: string;

  @Input("userProfile") activeProfileInfo: ProfileState;

  @Input("isGuestProfile") public isGuestProfile: boolean = false;

  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  private isProfileHasPendingFrRequest$: Subscription

  public pendingFrRequest: boolean;

  constructor(private route: ActivatedRoute,
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
          data: "You have send a friend request"
        })
      }, error => console.log(throwError(error)));
  }

  acceptFriendRequest() {

  }

  ngOnDestroy(): void {
    this.isProfileHasPendingFrRequest$.unsubscribe();
  }
}
