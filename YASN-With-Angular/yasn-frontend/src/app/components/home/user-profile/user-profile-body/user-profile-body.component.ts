import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileState} from "../../../../core/store/app.state";

@Component({
  selector: 'app-user-profile-body',
  templateUrl: './user-profile-body.component.html',
  styleUrls: ['./user-profile-body.component.css']
})
export class UserProfileBodyComponent implements OnInit {
  @Input("selectedProfileId") selectedProfileId: string;
  @Input("userProfile") activeProfileInfo: ProfileState;
  @Input("isGuestProfile") public isGuestProfile: boolean = false;
  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {

  }

}
