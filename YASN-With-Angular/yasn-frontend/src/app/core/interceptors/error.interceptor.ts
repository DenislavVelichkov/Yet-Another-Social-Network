import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from "../services/authentication/auth.service";
import {Router} from "@angular/router";
import {AppState} from "../store/app.state";
import {Store} from "@ngrx/store";
import {AuthenticatingFailedAction} from "../store/authentication/actions/authenticating-failed.action";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router,
              private auth: AuthService,
              private store: Store<AppState>) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(req).pipe(catchError(err => {
      this.store.dispatch(new AuthenticatingFailedAction({error: err}))

      if (err.status === 401) {
        // auto logout if 401 response returned from api
        location.reload();
      }

      if (err.status === 404) {
        this.router.navigate(['404']).catch(reason => console.log(throwError(reason)));
      }

      if (err.status === 403) {
        this.router.navigate(['index']).catch(reason => console.log(throwError(reason)));
      } else {
        this.router.navigate(['error']).catch(reason => console.log(throwError(reason)));
      }

      this.auth.logout();
      console.log(err);

      return throwError(err);
    }))
  }
}
