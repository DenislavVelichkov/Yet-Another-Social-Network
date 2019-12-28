import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexComponent} from './components/index/index.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { NavbarModule } from './shared/components/navbar/navbar.module';
import { UserModule } from './components/user/user.module';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NavbarModule,
    UserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
