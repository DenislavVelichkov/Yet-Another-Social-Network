import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {AppState} from "../store/app.state";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {map, take} from "rxjs/operators";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private store: Store<AppState>,
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.store.select('auth').pipe(
      take(1),
      map(value => {
        const isAuth = !!value.activeUser;
        if (isAuth) {
          return true;
        }

        return this.router.createUrlTree(['user/login']);
      }));
  }
}
