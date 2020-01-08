import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpXsrfTokenExtractor} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class SessionInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService,
              private tokenExtractor: HttpXsrfTokenExtractor,
              private cookieService: CookieService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.auth.currentUserInfo;

    if (currentUser && currentUser.authData) {
      const csrfToken = this.tokenExtractor.getToken();
      const sessionId = currentUser.sessionId;

      request = request.clone({
        setHeaders: {
          'X-XSRF-TOKEN': `${csrfToken}`,
          'X-Auth-Token': `${sessionId}`
        },
      });
      this.cookieService.set('XSRF-TOKEN', csrfToken)
    }

    return next.handle(request);
  }
}
