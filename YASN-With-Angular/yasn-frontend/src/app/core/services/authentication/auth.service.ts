import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {UserLoginBindingModel} from "../../../shared/models/user/UserLoginBindingModel";
import {throwError} from "rxjs";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {Store} from "@ngrx/store";
import {AppState} from "../../store/app.state";
import {Principal} from "../../store/authentication/Principal";
import {HttpResponse} from "@angular/common/http";
import {AuthenticatedAction} from "../../store/authentication/actions/authenticated.action";
import {LogoutAction} from "../../store/authentication/actions/logout.action";

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

    let authenticatedUser: Principal =
      {
        userProfileId: payload.userProfileId,
        role: payload.role,
        fullName: payload.fullName,
        avatarUrl: payload.avatarUrl,
        coverPictureUrl: payload.coverPictureUrl,
        _token: token,
        tokenExpirationDate: expirationDate,
        rememberMe: true
      }

    localStorage.setItem('activeUser', JSON.stringify(authenticatedUser))
    this.cookieService.deleteAll();
    this.store.dispatch(new AuthenticatedAction(authenticatedUser))
  }
}
