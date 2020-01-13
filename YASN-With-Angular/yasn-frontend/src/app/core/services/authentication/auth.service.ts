import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {UserLoginBindingModel} from "../../../shared/models/user/UserLoginBindingModel";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {ActiveUser} from "../../store/authentication/ActiveUser";
import {User} from "../../store/authentication/User";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class AuthService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<ActiveUser>;
  public userCredentials: string;

  constructor(private httpRepo: HttpRepositoryService,
              private cookieService: CookieService,
              private router: Router) {

    this.currentUserSubject = new BehaviorSubject(
      JSON.parse(localStorage.getItem('activeUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  loginUser(userModel: UserLoginBindingModel): void {
    const formCredentials =
      {email: userModel.email, password: userModel.password};
    this.userCredentials =
      window.btoa(formCredentials.email + ':' + formCredentials.password);

    let params = new FormData();
    params.append('email', `${userModel.email}`);
    params.append('password', `${userModel.password}`);

    this.httpRepo.loginRequest("/user/login", params)
      .subscribe(() => {
          this.httpRepo.getActiveUser("/user/principal")
            .subscribe(user => {
                user.authData = this.userCredentials;
                localStorage.setItem('activeUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
                this.router.navigate(['/home']);

                return user;
              }, error => {
                this.router.navigate(['/']);
                throwError(error)
              }
            );
        },
        error => throwError(error));
  }

  logout() {
    localStorage.removeItem('activeUser');
    this.currentUserSubject.next(null);
    this.cookieService.deleteAll();
    this.router.navigate(['/']);
  }

  public get currentUserInfo(): User {
    return this.currentUserSubject.value;
  }
}
