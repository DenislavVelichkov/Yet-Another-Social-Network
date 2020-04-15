import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from "../services/authentication/auth.service";
import {Router} from "@angular/router";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router,
              private auth: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {

      if (err.status === 401) {
        // auto logout if 401 response returned from api
        this.auth.logout();
        location.reload();
      }

      if (err.status === 404) {
        this.router.navigate(['404']);
      }

      if (err.status === 403) {
        this.router.navigate(['user/login']);
      }

      this.router.navigate(['error']);

      return throwError(err);
    }))
  }
}
