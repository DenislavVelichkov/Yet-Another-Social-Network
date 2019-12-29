import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegisterComponent } from './user-register/user-register.component';
import { UserComponent } from './user.component';
import { MatDatepickerModule } from '@angular/material/datepicker';




@NgModule({
  declarations: [
    UserLoginComponent,
    UserRegisterComponent,
    UserComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatDatepickerModule
  ],
  exports: [UserComponent]
})
export class UserModule { }
