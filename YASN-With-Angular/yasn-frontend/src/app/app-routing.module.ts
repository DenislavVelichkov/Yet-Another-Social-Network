import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "./components/index/index.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  { path: "", pathMatch: "full", component: IndexComponent},
  { path: "user/login", component:IndexComponent},
  { path: "home", component:HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
