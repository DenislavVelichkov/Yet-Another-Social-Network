import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.auth.loggedInUser;

    if (currentUser && this.auth.getAuthData()) {
      request = request.clone({
        setHeaders:
          {
            Authorization: `Basic ${this.auth.getAuthData()}`
          }
      });
    }

    return next.handle(request);
  }
}
