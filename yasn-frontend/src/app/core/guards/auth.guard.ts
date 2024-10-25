import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import {Observable} from "rxjs";
import {AuthService} from "../services/authentication/auth.service";


@Injectable({providedIn: 'root'})
export class AuthGuard  {
  private isAuth: boolean;

  constructor(private router: Router,
              private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.isAuth = this.authService.isUserLoggedIn();

    let result;

    if (this.isAuth) {
      result = true;
    } else {
      result = this.router.createUrlTree(['user/login']);
    }


    return result;
  }
}
