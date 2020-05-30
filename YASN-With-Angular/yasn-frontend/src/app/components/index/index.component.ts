import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../core/services/authentication/auth.service";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  isAuthenticated: boolean;

  constructor(private authService: AuthService) {

  }

  ngOnInit() {
    this.authService.isUserLoggedIn().subscribe(value => {
      this.isAuthenticated = value;
    });

  }
}
