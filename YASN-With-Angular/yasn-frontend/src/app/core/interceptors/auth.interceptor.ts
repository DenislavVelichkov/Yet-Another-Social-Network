import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthState} from "../store/authentication/state/auth.state";
import {Store} from "@ngrx/store";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store:Store<AuthState>) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.store.select('activeUser').authData;

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
