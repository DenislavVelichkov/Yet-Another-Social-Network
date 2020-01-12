import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/authentication/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.auth.currentUserInfo;

    if (currentUser && currentUser.authData) {
      request = request.clone({
        setHeaders:
          {
            Authorization: `Basic ${currentUser.authData}`
          }
      });
    }

    return next.handle(request);
  }
}
