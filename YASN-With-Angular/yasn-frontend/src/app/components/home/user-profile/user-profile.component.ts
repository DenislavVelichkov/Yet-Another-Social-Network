import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState, ProfileState} from "../../../core/store/app.state";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../core/services/authentication/auth.service";
import {GetGuestProfileDetails} from "../../../core/store/guestProfile/actions/get-guest-profile.details";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {ProfileInfoModel} from "../../../shared/models/user/ProfileInfoModel";
import {UpdateActiveProfileAction} from "../../../core/store/userProfile/actions/update-active-profile.action";
import {AcceptFrRequestAction} from "../../../core/store/on-action/actions/accept-fr-request.action";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public selectedProfileState: ProfileState;

  public selectedProfileId: string;

  public loggedInProfileId: string;

  public isGuestProfile: boolean = false;

  public isProfileOwnerLoggedIn: boolean = false;

  public areTheyFriends: boolean = false;

  constructor(private store$: Store<AppState>,
              private route: ActivatedRoute,
              private auth: AuthService,
              private http: HttpRepositoryService) {
  }

  ngOnInit(): void {
    this.loggedInProfileId =
      JSON.parse(localStorage.getItem("activeUser"))._userProfileId;

    this.route.paramMap.subscribe(value => {
      this.selectedProfileId = value.get("id");
      this.updateComponent();
    })

  }

  updateComponent() {
    this.store$.select('onAction').subscribe(data => this.areTheyFriends = data.acceptFrRequest);

    this.http.get<boolean>(EndpointUrls.friendshipStatus + `${this.loggedInProfileId}` + "/" + `${this.selectedProfileId}`)
      .pipe(take(1)).subscribe(data => {

      this.store$.dispatch(new AcceptFrRequestAction({acceptFrRequest: data}));
    }, error => console.log(new Error(error)));


    if (this.loggedInProfileId !== this.selectedProfileId) {
      this.http.get<ProfileInfoModel>(EndpointUrls.selectUserProfile + this.selectedProfileId)
        .pipe(take(1))
        .subscribe(value => {
          this.store$.dispatch(new GetGuestProfileDetails(value));
        }, error => console.log(new Error(error)));

      this.store$.select('guestProfile').subscribe(data => {
        this.selectedProfileState = data;
        this.isGuestProfile = true;
        this.isProfileOwnerLoggedIn = false;

      }, error => console.log(new Error(error)));

    } else {
      this.http.get<ProfileInfoModel>(EndpointUrls.selectUserProfile + this.selectedProfileId)
        .pipe(take(1))
        .subscribe(value => {
          this.store$.dispatch(new UpdateActiveProfileAction(value));
        }, error => console.log(new Error(error)));

      this.store$.select('userProfile').subscribe(data => {
        this.selectedProfileState = data;
        this.isGuestProfile = false;
        this.isProfileOwnerLoggedIn = true;
      });
    }
  }

}
