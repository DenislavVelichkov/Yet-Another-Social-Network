import {Component, Input, OnInit} from '@angular/core';
import {UserProfileState} from "../../../../core/store/userProfile/state/user-profile.state";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile-header',
  templateUrl: './user-profile-header.component.html',
  styleUrls: ['./user-profile-header.component.css']
})
export class UserProfileHeaderComponent implements OnInit {
  @Input("userProfile") userProfileInfo: UserProfileState;
  public currentProfileId: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.currentProfileId = this.route.snapshot.paramMap.get('id');
  }

}
