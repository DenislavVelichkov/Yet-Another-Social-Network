import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../core/services/auth.service";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private auth:AuthService, private cookieService: CookieService) {
    console.log(this.cookieService.check('XSRF-TOKEN'))

  }

  ngOnInit() {
  }

}
