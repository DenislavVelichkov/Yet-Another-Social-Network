import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from "./components/footer/footer.component";
import {AuthService} from "../core/services/auth.service";
import {NavbarModule} from "./components/navbar/navbar.module";
import {NavbarComponent} from "./components/navbar/navbar.component";

@NgModule({
  declarations: [
    FooterComponent
  ],
  imports: [
    CommonModule,
    NavbarModule
  ],
  exports:[FooterComponent, NavbarComponent]
})
export class SharedModule {
  /*Ensure one instance of the services*/
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [AuthService]
    };
  }
}
