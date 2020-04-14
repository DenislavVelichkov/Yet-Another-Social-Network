import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Store} from "@ngrx/store";
import {AppState} from "../store/app.state";
import {exhaustMap, take} from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private store: Store<AppState>) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.store.select('auth').pipe(take(1), exhaustMap(value => {

      if (!value.activeUser) {
        return next.handle(request);
      }

      const modifiedReq = request.clone({
        headers: new HttpHeaders().append("Authorization", value.authData)
      });

      return next.handle(modifiedReq);
    }));
  }
}
