import {Component, Input, OnInit} from '@angular/core';
import {UserProfileState} from "../../../../core/store/userProfile/state/user-profile.state";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile-body',
  templateUrl: './user-profile-body.component.html',
  styleUrls: ['./user-profile-body.component.css']
})
export class UserProfileBodyComponent implements OnInit {
  @Input("userProfile") activeProfileInfo: UserProfileState;
  public selectedProfileId: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.selectedProfileId = this.route.snapshot.paramMap.get('id');
  }

}
