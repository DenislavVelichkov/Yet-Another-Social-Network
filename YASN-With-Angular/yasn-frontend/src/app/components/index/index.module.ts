import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IndexComponent} from './index.component';
import {UserLoginComponent} from '../user/user-login/user-login.component';
import {UserRegisterComponent} from '../user/user-register/user-register.component';
import {UserService} from 'src/app/core/services/user/user.service';
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
  exports: [IndexComponent]
})

export class IndexModule {
  /*Ensure one instance of the services*/
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: IndexModule,
      providers: [UserService]
    };
  }
}
