import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthorizedNavbarComponent} from './authorized-navbar/authorized-navbar.component';
import {UnAuthorizedNavbarComponent} from './un-authorized-navbar/un-authorized-navbar.component';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar.component';
import {AppRoutingModule} from "../../../app-routing.module";

@NgModule({
  declarations: [
    AuthorizedNavbarComponent,
    UnAuthorizedNavbarComponent,
    NavbarComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        AppRoutingModule,
    ],
    exports: [NavbarComponent]
})
export class NavbarModule { }
