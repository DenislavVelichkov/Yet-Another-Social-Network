import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit {
  private profilePictureUrl: string = 'alavala';
  private profileId: string;
  private userName: string;

  constructor(private auth: AuthService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.select('auth').subscribe(value => {
      this.profileId = value.activeUser.userProfileId;
      this.userName = value.activeUser.userName;
    })
  }

  logout() {
    this.auth.logout();
  }
}
