import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexModule} from './components/index/index.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CoreModule} from "./core/core.module";
import {AuthService} from "./core/services/authentication/auth.service";
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";
import {CookieService} from "ngx-cookie-service";
import {AuthInterceptor} from "./core/interceptors/auth.interceptor";
import {HomeModule} from "./components/home/home.module";
import {ErrorInterceptor} from "./core/interceptors/error.interceptor";

@NgModule({
  declarations: [
    AppComponent,
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CoreModule.forRoot(),
    IndexModule.forRoot(),
    HomeModule
  ],

  providers: [
    AuthService,
    CookieService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],

  bootstrap: [AppComponent],
})
export class AppModule {
}
