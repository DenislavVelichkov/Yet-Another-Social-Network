import {Component, OnInit} from '@angular/core';
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  public userProfileState: UserProfileState;
  public userProfile = 'userProfile';

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.select('userProfile').pipe(take(1)).subscribe(data => {
      this.userProfileState = data;
    })
  }

}
