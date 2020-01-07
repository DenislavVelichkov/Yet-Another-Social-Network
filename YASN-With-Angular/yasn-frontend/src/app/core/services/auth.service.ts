import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../shared/services/http-repository.service";
import {UserLoginBindingModel} from "../../shared/models/user/UserLoginBindingModel";
import {HttpHeaders} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class AuthService {
  public isAuthenticated: boolean;
  public loggedInUser: any;
  public userCredentials: string;

  constructor(private httpRepo: HttpRepositoryService) {
    this.isAuthenticated = false;
  }

  loginUser(userModel: UserLoginBindingModel): void {
    const formCredentials = {email: userModel.email, password: userModel.password};
    this.userCredentials = btoa(formCredentials.email + ':' + formCredentials.password);
    const formHeader = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
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

    this.httpRepo.getData("/user/principal", credentialsHeader)
      .subscribe(value => {
        if (value !== null) {
          this.loggedInUser = value;

          this.isAuthenticated = true;
        }
      }, error => {
        console.log(error);
      });
  }

  logout() {
    this.httpRepo.create("/logout", {}).subscribe();
  }

  getAuthData(): string {
    return `${this.userCredentials}`
  }
}
