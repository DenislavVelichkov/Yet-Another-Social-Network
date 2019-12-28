import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthorizedNavbarComponent } from './authorized-navbar/authorized-navbar.component';
import { UnAuthorizedNavbarComponent } from './un-authorized-navbar/un-authorized-navbar.component';

@NgModule({
  declarations: [
    AuthorizedNavbarComponent,
    UnAuthorizedNavbarComponent
  ],
  imports: [
    FormsModule,
  ],
  providers:[]
})
export class NavbarModule { }
