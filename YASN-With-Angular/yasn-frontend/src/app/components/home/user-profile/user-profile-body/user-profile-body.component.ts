import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppState, ProfileState} from "../../../../core/store/app.state";
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-user-profile-body',
  templateUrl: './user-profile-body.component.html',
  styleUrls: ['./user-profile-body.component.css']
})
export class UserProfileBodyComponent implements OnInit {

  @Input("selectedProfileId") selectedProfileId: string;

  @Input("loggedInProfileId") public loggedInProfileId: string;

  @Input("profileOwnerState") public profileOwnerState: ProfileState;

  @Input("isGuestProfile") public isGuestProfile: boolean = false;

  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  public areTheyFriends: boolean = false;

  constructor(private route: ActivatedRoute,
              private http: HttpRepositoryService,
              private store$: Store<AppState>) {}

  ngOnInit(): void {
    this.http.get<boolean>(EndpointUrls.friendshipStatus + `${this.loggedInProfileId}` + "/" + `${this.selectedProfileId}`)
      .pipe(take(1)).subscribe(data => {this.areTheyFriends = data; console.log(this.areTheyFriends)});

    this.store$.select('onAction').subscribe(data => this.areTheyFriends = data.acceptFrRequest);
  }

}
