import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthorizedNavbarComponent} from './authorized-navbar/authorized-navbar.component';
import {UnAuthorizedNavbarComponent} from './un-authorized-navbar/un-authorized-navbar.component';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar.component';
import {RouterModule} from "@angular/router";
import {MatBadgeModule} from "@angular/material/badge";

@NgModule({
  declarations: [
    AuthorizedNavbarComponent,
    UnAuthorizedNavbarComponent,
    NavbarComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        MatBadgeModule,
    ],
    exports: [NavbarComponent]
})
export class NavbarModule { }
