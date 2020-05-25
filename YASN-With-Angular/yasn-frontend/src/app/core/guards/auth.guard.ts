import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {AppState} from "../store/app.state";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {take} from "rxjs/operators";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private store: Store<AppState>,
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    let result = null;

    this.store.select('auth').pipe(take(1)).subscribe(data => {
      const isAuth = !!data.activeUser;

      if (isAuth) {
        result = true;
      } else {
        result = this.router.createUrlTree(['user/login']);
      }

    });

    return result;
  }
}
