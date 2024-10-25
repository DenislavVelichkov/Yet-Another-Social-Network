import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "./components/index/index.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {ErrorComponent} from "./components/error/error.component";
import {PageNotFoundComponent} from "./components/error/page-not-found/page-not-found.component";
import {UserProfileComponent} from "./components/home/user-profile/user-profile.component";
import {LoggedInGuard} from "./core/guards/logged-in.guard";

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: "404", component: PageNotFoundComponent},
  {path: "error", component: ErrorComponent},
  {path: "home", canActivate: [AuthGuard], component: HomeComponent},
  {path: "index", canActivate: [AuthGuard], component: IndexComponent},
  {path: "user/login", canActivate: [LoggedInGuard], component: IndexComponent},
  {path: "user/register", canActivate: [LoggedInGuard], component: IndexComponent},
  {path: "user/profile/:id", canActivate: [AuthGuard], component: UserProfileComponent},
  {path: "**", redirectTo: '/404'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {
        scrollPositionRestoration: "enabled",
      })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
