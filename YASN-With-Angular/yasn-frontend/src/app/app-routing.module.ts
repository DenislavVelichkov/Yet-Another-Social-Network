import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from "./components/index/index.component";
import { NavbarModule } from './shared/components/navbar/navbar.module';


const routes: Routes = [
  { path: "", pathMatch: "full", component: IndexComponent },
  // { path: "", pathMatch: "full", loadChildren: () => NavbarModule },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
