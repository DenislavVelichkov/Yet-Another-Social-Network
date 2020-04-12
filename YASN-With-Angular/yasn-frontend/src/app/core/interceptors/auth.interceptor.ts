import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Store} from "@ngrx/store";
import {AppState} from "../store/app.state";
import {Principal} from "../store/authentication/Principal";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private currentUser: Observable<Principal>;

  constructor(private store:Store<AppState>) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.currentUser = this.store.select('auth')['activeUser'];

    if (this.currentUser && this.currentUser['activeUser'].authData) {
      request = request.clone({
        setHeaders:
          {
            Authorization: `Basic ${this.currentUser['activeUser'].authData}`
          }
      });
    }

    return next.handle(request);
  }
}
