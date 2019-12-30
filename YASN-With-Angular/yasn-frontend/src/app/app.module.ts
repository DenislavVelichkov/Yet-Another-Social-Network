import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { NavbarModule } from './shared/components/navbar/navbar.module';
import { IndexModule } from './components/index/index.module';
import { AuthService } from './core/services/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NavbarModule,
    IndexModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
