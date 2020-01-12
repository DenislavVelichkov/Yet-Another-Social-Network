import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit {

  constructor(private auth: AuthService) {
  }

  ngOnInit() {
  }

  logout() {
    this.auth.logout();
  }
}
