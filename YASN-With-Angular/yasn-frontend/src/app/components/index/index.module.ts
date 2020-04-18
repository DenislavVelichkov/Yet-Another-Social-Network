import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IndexComponent} from './index.component';
import {UserLoginComponent} from '../user/user-login/user-login.component';
import {UserRegisterComponent} from '../user/user-register/user-register.component';
import {RouterModule} from "@angular/router";
import {PickerModule} from "@ctrl/ngx-emoji-mart";

@NgModule({
  declarations: [
    UserLoginComponent,
    UserRegisterComponent,
    IndexComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        PickerModule
    ],
  exports: [IndexComponent]
})

export class IndexModule {

}
