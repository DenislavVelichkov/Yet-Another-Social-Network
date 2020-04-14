import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "./components/index/index.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {ErrorComponent} from "./components/error/error.component";

const routes: Routes = [
  {path: "**", redirectTo: "/404"},
  {path: "", pathMatch: "full", component: IndexComponent},
  {path: "index", component: IndexComponent},
  {path: "404", component: ErrorComponent},
  {path: "error", component: ErrorComponent},
  {path: "home", canActivate: [AuthGuard], component: HomeComponent},
  {path: "user/login", component: IndexComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
