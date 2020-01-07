import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  private isAuthenticated: boolean;

  constructor(private auth: AuthService) {
    this.isAuthenticated = this.auth.isAuthenticated;
    console.log(this.isAuthenticated);
    console.log(this.auth.isAuthenticated);
  }

  ngOnInit() {

    console.log('ngOnInit' + this.isAuthenticated);
    console.log('ngOnInit' + this.auth.isAuthenticated);

  }

}
