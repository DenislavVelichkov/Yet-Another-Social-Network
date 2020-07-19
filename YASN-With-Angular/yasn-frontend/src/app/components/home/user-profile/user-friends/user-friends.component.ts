import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../core/services/authentication/auth.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {UserProfileFriendModel} from "../../../../shared/models/user/UserProfileFriendModel";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-friends',
  templateUrl: './user-friends.component.html',
  styleUrls: ['./user-friends.component.css']
})
export class UserFriendsComponent implements OnInit, OnDestroy {

  private selectedProfileId: string;

  friendsSubscription: Subscription

  friends: UserProfileFriendModel[];

  constructor(private http: HttpRepositoryService,
              private auth: AuthService,
              private route: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(value => {
      this.selectedProfileId = value.get("id");
    });

    this.friendsSubscription = this.http.get<Array<UserProfileFriendModel>>(
      EndpointUrls.getUserProfileFriendsWithDetails + this.selectedProfileId)
      .subscribe(value => {
          this.friends = value;
      },
          error => console.log(new Error(error)));
  }

  ngOnDestroy(): void {
    this.friendsSubscription.unsubscribe();
  }

}
