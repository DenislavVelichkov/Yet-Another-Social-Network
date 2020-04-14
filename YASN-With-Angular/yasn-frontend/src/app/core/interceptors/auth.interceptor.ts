import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Store} from "@ngrx/store";
import {AppState} from "../store/app.state";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private store: Store<AppState>) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.store.select('auth').subscribe(value => {

      if (!value.authData) {
        return next.handle(request);
      }

      if (value.authData && value.isAuthenticated) {
        request = request.clone({
          setHeaders:
            {
              Authorization: `${value.authData}`
            }
        });
      }

    }, error => throwError(error));

    return next.handle(request);
  }
}
