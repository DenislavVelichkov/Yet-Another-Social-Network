import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "./components/index/index.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {ErrorComponent} from "./components/error/error.component";
import {throwError} from "rxjs";
import {PageNotFoundComponent} from "./components/error/page-not-found/page-not-found.component";
import {UserProfileComponent} from "./components/home/user-profile/user-profile.component";

const routes: Routes = [
  {path: "404", component: PageNotFoundComponent},
  {path: "", pathMatch: "full", component: IndexComponent},
  {path: "index", component: IndexComponent},
  {path: "error", component: ErrorComponent},
  {path: "home", canActivate: [AuthGuard], component: HomeComponent},
  {path: "index", component: IndexComponent},
  {path: "user/login", component: IndexComponent},
  {path: "user/profile", canActivate: [AuthGuard], component: UserProfileComponent},
  {path: "home/user/profile/:id", canActivate: [AuthGuard], component: UserProfileComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {scrollPositionRestoration: "disabled", errorHandler: error => throwError(console.log(error))})
  ],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
