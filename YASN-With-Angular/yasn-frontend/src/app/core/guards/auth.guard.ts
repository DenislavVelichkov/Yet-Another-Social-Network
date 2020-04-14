import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AppState} from "../store/app.state";
import {Store} from "@ngrx/store";


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  private isAuthenticated: boolean;

  constructor(
    private router: Router,
    private store: Store<AppState>,
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    this.store.select('auth').subscribe(value => {
      this.isAuthenticated = value.isAuthenticated;
    });

    if (this.isAuthenticated) {return true;}

    this.router.navigate(['/index'], { queryParams: { returnUrl: state.url } });

    return false;
  }
}
