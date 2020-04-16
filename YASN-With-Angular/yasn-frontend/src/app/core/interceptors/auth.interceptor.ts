import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Store} from "@ngrx/store";
import {AppState} from "../store/app.state";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private activeUser;

  constructor(private store: Store<AppState>) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      this.store.select('auth').subscribe(source => this.activeUser = source.activeUser);

      if (this.activeUser) {
        const headers = new HttpHeaders({
          ['Authorization']:`${this.activeUser._token}`
        })

        const modifiedReq = request.clone({headers});

        return next.handle(modifiedReq);
      }

    return next.handle(request);
  }
}
