import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../shared/services/http-repository.service";
import {CookieService} from "ngx-cookie-service";
import {UserLoginBindingModel} from "../../shared/models/user/UserLoginBindingModel";
import {HttpHeaders} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class AuthService {
  public isAuthenticated: boolean;

  constructor(private httpRepo: HttpRepositoryService,
              private cookieService: CookieService) {
  }

  loginUser(userModel: UserLoginBindingModel): boolean {
    const credentials = {email: userModel.email, password: userModel.password};
    const formHeader = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    const credentialsHeader = new HttpHeaders(credentials ?
      {
        Authorization: 'Basic ' + btoa(credentials.email + ':' + credentials.password)
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
          this.isAuthenticated = true;
          console.log(value);
          this.saveToken(value)
        }
      }, error => {
        this.isAuthenticated = false;
        console.log(error)
      });

    // this.httpRepo.getData("/user/token", credentialsHeader).subscribe(value => console.log(JSON.stringify(value)));

    return false;
  }

  saveToken(token: any) {
    let expireDate = new Date().getTime() + (1000 * token.expires_in);

    this.cookieService.set('access_token', token.access_token, expireDate);
    this.cookieService.set('role', token.role);
    this.cookieService.set("Secure", "")

  }

}
