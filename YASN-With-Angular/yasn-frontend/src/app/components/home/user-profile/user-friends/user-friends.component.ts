import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../core/services/authentication/auth.service";
import {UserAuthModel} from "../../../../core/store/authentication/UserAuthModel";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {UserProfileFriendModel} from "../../../../shared/models/user/UserProfileFriendModel";

@Component({
  selector: 'app-user-friends',
  templateUrl: './user-friends.component.html',
  styleUrls: ['./user-friends.component.css']
})
export class UserFriendsComponent implements OnInit, OnDestroy {

  private activeUser: UserAuthModel;

  friendsSubscription: Subscription

  friends: UserProfileFriendModel[];

  constructor(private http: HttpRepositoryService,
              private auth: AuthService) {
  }

  ngOnInit(): void {
    this.activeUser = this.auth.getActiveUser();
    this.friendsSubscription = this.http.get<Array<UserProfileFriendModel>>(
      EndpointUrls.getUserProfileFriendsWithDetails + this.activeUser.userProfileId)
      .subscribe(value => {
          this.friends = value;
      },
          error => console.log(new Error(error)));
  }

  ngOnDestroy(): void {
    this.friendsSubscription.unsubscribe();
  }

}
