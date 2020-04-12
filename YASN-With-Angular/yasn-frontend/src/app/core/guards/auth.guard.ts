import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "../services/authentication/auth.service";
import {AppState} from "../store/app.state";


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private auth: AuthService,
    private appState: AppState
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const activeUser = this.appState.auth.isAuthenticated

    if (activeUser) {return true;}

    this.router.navigate(['user/login'], { queryParams: { returnUrl: state.url } });

    return false;
  }
}
