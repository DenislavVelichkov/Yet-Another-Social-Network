import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import {take} from "rxjs/operators";
import {AuthService} from "../services/authentication/auth.service";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
  private isAuth: boolean;

  constructor(
    private router: Router,
    private authService: AuthService,
  ) {
  }

  canActivate(

    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.authService.isUserLoggedIn().pipe(take(1)).subscribe(value => this.isAuth = value);

    let result;

      if (this.isAuth) {
        result = true;
      } else {
        result = this.router.createUrlTree(['user/login']);
      }


    return result;
  }
}
