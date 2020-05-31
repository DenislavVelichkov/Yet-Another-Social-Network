import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileState} from "../../../../core/store/app.state";

@Component({
  selector: 'app-user-profile-header',
  templateUrl: './user-profile-header.component.html',
  styleUrls: ['./user-profile-header.component.css']
})
export class UserProfileHeaderComponent implements OnInit {
  public selectedProfileId: string;
  @Input("userProfile") activeProfileInfo: ProfileState;
  @Input("isGuestProfile") public isGuestProfile: boolean = false;
  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.selectedProfileId = this.route.snapshot.paramMap.get('id');
  }

}
