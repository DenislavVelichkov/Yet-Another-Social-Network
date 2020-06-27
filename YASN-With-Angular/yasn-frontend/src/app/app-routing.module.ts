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
  {path: "", canActivate: [LoggedInGuard], redirectTo: "/user/login", pathMatch: "full"},
  {path: "404", component: PageNotFoundComponent},
  {path: "error", component: ErrorComponent},
  {path: "home", canActivate: [AuthGuard], component: HomeComponent},
  {path: "user/login", canActivate: [LoggedInGuard], component: IndexComponent},
  {path: "user/register", canActivate: [LoggedInGuard], component: IndexComponent},
  {path: "user/profile/:id", canActivate: [AuthGuard], component: UserProfileComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {
        scrollPositionRestoration: "enabled",
        /*errorHandler: error => {
          console.log(throwError(error));
          window.location.replace("http://localhost:4200/404");
        }*/
      })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
