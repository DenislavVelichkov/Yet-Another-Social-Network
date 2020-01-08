import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private auth: AuthService) {}

  ngOnInit() {
  }

  isAuthenticated(): boolean {
    console.log(this.auth.isAuthenticated);
    return this.auth.isAuthenticated;
  }
}
