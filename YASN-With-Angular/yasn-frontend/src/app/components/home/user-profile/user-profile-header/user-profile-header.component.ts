import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileState} from "../../../../core/store/app.state";
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {Notification} from "../../../../core/store/notification/Notification";

@Component({
  selector: 'app-user-profile-header',
  templateUrl: './user-profile-header.component.html',
  styleUrls: ['./user-profile-header.component.css']
})
export class UserProfileHeaderComponent implements OnInit {
  @Input("selectedProfileId") selectedProfileId: string;
  @Input("userProfile") activeProfileInfo: ProfileState;
  @Input("isGuestProfile") public isGuestProfile: boolean = false;
  @Input("isActiveProfile") public isActiveProfile: boolean = false;

  constructor(private route: ActivatedRoute,
              private http: HttpRepositoryService) {
  }

  ngOnInit(): void {

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
        console.log(JSON.stringify(data))
      });
  }
}
