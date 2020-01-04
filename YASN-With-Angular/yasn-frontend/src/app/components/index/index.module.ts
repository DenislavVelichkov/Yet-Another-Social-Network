import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IndexComponent} from './index.component';
import {UserLoginComponent} from '../user/user-login/user-login.component';
import {UserRegisterComponent} from '../user/user-register/user-register.component';
import {UserService} from 'src/app/components/user/user.service';
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    UserLoginComponent,
    UserRegisterComponent,
    IndexComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule
    ],
  providers: [UserService],
  exports: [IndexComponent]
})
export class IndexModule { }
