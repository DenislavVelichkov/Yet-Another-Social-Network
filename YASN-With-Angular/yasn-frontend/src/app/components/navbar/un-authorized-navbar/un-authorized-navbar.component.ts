import {Component, OnInit} from '@angular/core';
import {UserLoginBindingModel} from "../../../models/user/UserLoginBindingModel";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {AuthService} from "../../../../core/services/auth.service";

@Component({
  selector: 'app-un-authorized-navbar',
  templateUrl: './un-authorized-navbar.component.html',
  styleUrls: ['./un-authorized-navbar.component.css', '../navbar.component.css']
})
export class UnAuthorizedNavbarComponent implements OnInit {

  private userLoginBindingModel: UserLoginBindingModel;

  constructor(private auth: AuthService,
              private router: Router,
              private title: Title) {
  }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Log In');
    this.userLoginBindingModel = new UserLoginBindingModel();
  }

  onSubmit() {
    this.auth.loginUser(this.userLoginBindingModel);
  }
}
