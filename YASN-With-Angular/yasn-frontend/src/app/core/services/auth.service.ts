import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../shared/services/http-repository.service";
import {UserLoginBindingModel} from "../../shared/models/user/UserLoginBindingModel";
import {HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {Router} from "@angular/router";
import {ActiveUser} from "../../shared/models/user/ActiveUser";
import {User} from "../../shared/models/user/User";

@Injectable({providedIn: 'root'})
export class AuthService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<ActiveUser>;
  public userCredentials: string;

  constructor(private httpRepo: HttpRepositoryService,
              private router: Router) {

    this.currentUserSubject = new BehaviorSubject(
      JSON.parse(localStorage.getItem('activeUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  loginUser(userModel: UserLoginBindingModel): void {
    const formCredentials = {email: userModel.email, password: userModel.password};
    this.userCredentials =
      btoa(formCredentials.email + ':' + formCredentials.password);
    const formHeader =
      new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    const credentialsHeader = new HttpHeaders(formCredentials ?
      {
        Authorization: 'Basic ' + this.userCredentials
      }
      : {},
    );

    this.httpRepo.loginRequest(
      "/user/login",
      `email=${userModel.email}&password=${userModel.password}`,
      {headers: formHeader})
      .subscribe();

    this.httpRepo.getActiveUser("/user/principal", credentialsHeader)
      .subscribe(user => {
          user.authData = this.userCredentials;
          localStorage.setItem('activeUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          this.router.navigate(['/home']);

          return user;
        }, error => {
          console.log(error);
          this.router.navigate(['/']);
        }
      );
  }

  logout() {
    this.httpRepo.create("/logout", {}).subscribe();
    localStorage.removeItem('activeUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/']);
  }

  public get currentUserInfo(): User {
    return this.currentUserSubject.value;
  }
}
