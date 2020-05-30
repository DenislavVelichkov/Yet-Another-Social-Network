import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {UserLoginBindingModel} from "../../../shared/models/user/UserLoginBindingModel";
import {throwError} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {Store} from "@ngrx/store";
import {AppState} from "../../store/app.state";
import {HttpResponse} from "@angular/common/http";
import {AuthenticatedAction} from "../../store/authentication/actions/authenticated.action";
import {LogoutAction} from "../../store/authentication/actions/logout.action";
import {UserAuthModel} from "../../store/authentication/UserAuthModel";
import {AuthenticatingFailedAction} from "../../store/authentication/actions/authenticating-failed.action";
import {take} from "rxjs/operators";
import {StopLoadingAction} from "../../store/loading/actions/stop-loading.action";
import {Router} from "@angular/router";

@Injectable({providedIn: "root"})
export class AuthService {

  constructor(private httpRepo: HttpRepositoryService,
              private cookieService: CookieService,
              private router: Router,
              private store: Store<AppState>) {
  }

  loginUser(userModel: UserLoginBindingModel): void {

    this.httpRepo.loginRequest("/api/user/login", userModel)
      .pipe(take(1))
      .subscribe((data: any) => {
          this.handleAuthentication(data)
          this.router.navigate(['/home']).catch(reason => console.log(throwError(reason)));
        },
        error => {
          this.store.dispatch(new AuthenticatingFailedAction({error: error}))
          this.store.dispatch(new StopLoadingAction({loading: false}))
          this.router.navigate(['/user/login']).catch(reason => throwError(reason))
          console.log(throwError(error));
        });
  }

  logout() {

    let nullPayload = {
      activeUser: false,
      isRegistered: false,
      isLoggedIn: false,
      error: null,
      isAuthenticated: false,
    }

    this.store.dispatch(new LogoutAction(nullPayload));

    localStorage.clear()

    this.router.navigate(['user/login']).catch(reason => throwError(reason));
  }

  public handleAuthentication(response: HttpResponse<any>) {
    const token: string = response.headers.get("Authorization")
    const payload = JSON.parse(atob((token.replace('Bearer: ', '').split('.')[1])));
    const expirationDate = new Date(new Date().getTime() + payload.exp);

    let authenticatedUser = new UserAuthModel(
      payload.role,
      payload.userId,
      payload.sub,
      true,
      token,
      expirationDate);

    localStorage.setItem('activeUser', JSON.stringify(authenticatedUser))
    this.store.dispatch(new AuthenticatedAction(
      {
        activeUser: authenticatedUser,
        isAuthenticated: true,
        isLoggedIn: true
      }));
    this.store.dispatch(new StopLoadingAction({loading: false}))
  }

  isUserLoggedIn(): boolean {
      return !!localStorage.getItem("activeUser")
  }

}
