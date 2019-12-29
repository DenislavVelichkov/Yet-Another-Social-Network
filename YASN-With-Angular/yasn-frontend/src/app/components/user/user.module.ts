import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegisterComponent } from './user-register/user-register.component';
import { UserComponent } from './user.component';




@NgModule({
  declarations: [
    UserLoginComponent,
    UserRegisterComponent,
    UserComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [UserComponent]
})
export class UserModule { }
