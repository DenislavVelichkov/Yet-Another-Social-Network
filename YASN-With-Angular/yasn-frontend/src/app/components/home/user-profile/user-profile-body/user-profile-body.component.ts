import {Component, Input, OnInit} from '@angular/core';
import {UserProfileState} from "../../../../core/store/userProfile/state/user-profile.state";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile-body',
  templateUrl: './user-profile-body.component.html',
  styleUrls: ['./user-profile-body.component.css']
})
export class UserProfileBodyComponent implements OnInit {
  @Input("userProfile") userProfileInfo: UserProfileState;
  public currentProfileId: string;


  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.currentProfileId = this.route.snapshot.paramMap.get('id');
  }

}
