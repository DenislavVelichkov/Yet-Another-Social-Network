import {Component, Injectable, OnInit} from '@angular/core';
import {UserLoginBindingModel} from "../../../shared/models/user/UserLoginBindingModel";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {StartLoadingAction} from "../../../core/store/loading/actions/start-loading.action";

@Component({
  selector: 'app-un-authorized-navbar',
  templateUrl: './un-authorized-navbar.component.html',
  styleUrls: ['./un-authorized-navbar.component.css']
})
@Injectable()
export class UnAuthorizedNavbarComponent implements OnInit {

  userLoginBindingModel: UserLoginBindingModel;

  constructor(private auth: AuthService,
              private router: Router,
              private title: Title,
              private store$: Store<AppState>) {
  }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Log In');
    this.userLoginBindingModel = new UserLoginBindingModel();
  }

  onSubmit() {
    this.store$.dispatch(new StartLoadingAction({loading: true}));
    this.auth.loginUser(this.userLoginBindingModel);
  }
}
