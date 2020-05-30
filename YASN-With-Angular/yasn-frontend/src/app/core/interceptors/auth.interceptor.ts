import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/authentication/auth.service";
import {take} from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private token: string;
  private userAlreadyLoggedIn: boolean;

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.authService.isUserLoggedIn()
      .pipe(take(1))
      .subscribe(value => this.userAlreadyLoggedIn = value);

    if (this.userAlreadyLoggedIn) {
      this.token = JSON.parse(localStorage.getItem("activeUser"))._token;
    }

    if (this.token && !request.hasOwnProperty('Authorization')) {
      const headers = new HttpHeaders({
        ['Authorization']: `${this.token}`
      })

      let modifiedReq = request.clone({headers});

      return next.handle(modifiedReq);
    }

    return next.handle(request);
  }
}
