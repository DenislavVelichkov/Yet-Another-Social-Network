import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {UserLoginBindingModel} from "../../../shared/models/user/UserLoginBindingModel";
import {throwError} from "rxjs";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {Store} from "@ngrx/store";
import {AppState} from "../../store/app.state";
import {HttpResponse} from "@angular/common/http";
import {AuthenticatedAction} from "../../store/authentication/actions/authenticated.action";
import {LogoutAction} from "../../store/authentication/actions/logout.action";
import {UserAuthModel} from "../../store/authentication/UserAuthModel";
import {AuthenticatingFailedAction} from "../../store/authentication/actions/authenticating-failed.action";

@Injectable({providedIn: "root"})
export class AuthService {

  constructor(private httpRepo: HttpRepositoryService,
              private cookieService: CookieService,
              private router: Router,
              private store: Store<AppState>) {
  }

  loginUser(userModel: UserLoginBindingModel): void {

    this.httpRepo.loginRequest("/api/user/login", userModel)
      .subscribe((data: any) => {
          this.handleAuthentication(data)
          this.router.navigate(['home']);
        },
        error => {
          this.store.dispatch(new AuthenticatingFailedAction({error: error, loading: false}))
          throwError(error)
        });
  }

  logout() {
    this.store.dispatch(new LogoutAction());
    localStorage.clear()
    this.router.navigate(['/user/login']).catch(reason => console.log(reason));
  }

  public handleAuthentication(response: HttpResponse<any>) {
    const token: string = response.headers.get("Authorization")
    const payload = JSON.parse(atob((token.replace('Bearer: ', '').split('.')[1])));
    const expirationDate = new Date(new Date().getTime() + payload.exp);

    let authenticatedUser = new UserAuthModel(payload.role,
                                              payload.userId,
                                              payload.sub,
                                              true,
                                              token,
                                              expirationDate);

    localStorage.setItem('activeUser', JSON.stringify(authenticatedUser))
    this.cookieService.deleteAll();
    this.store.dispatch(new AuthenticatedAction(authenticatedUser))
  }
}
