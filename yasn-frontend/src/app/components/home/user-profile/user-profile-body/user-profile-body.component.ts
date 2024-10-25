import {Component, Input, OnInit} from '@angular/core';
import {ProfileState} from "../../../../core/store/app.state";

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

  @Input("areTheyFriends") public areTheyFriends: boolean = false;

  constructor() {
  }

  ngOnInit(): void {

  }

}
