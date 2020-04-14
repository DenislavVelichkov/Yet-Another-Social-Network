import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {AuthState} from "../../../core/store/authentication/state/auth.state";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit {

  constructor(private auth: AuthService,
              private store: Store<AuthState>) {
  }

  ngOnInit() {

  }

  logout() {
    this.auth.logout();
  }
}
