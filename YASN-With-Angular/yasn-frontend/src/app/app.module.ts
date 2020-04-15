import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthService} from "./core/services/authentication/auth.service";
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";
import {CookieService} from "ngx-cookie-service";
import {AuthInterceptor} from "./core/interceptors/auth.interceptor";
import {ErrorInterceptor} from "./core/interceptors/error.interceptor";
import {RouterModule} from "@angular/router";
import {ErrorComponent} from "./components/error/error.component";
import {StoreModule} from "@ngrx/store";
import {appReducers} from "./core/store/app.reducers";
import {CommonModule} from "@angular/common";
import {CoreModule} from "./core/core.module";
import {PageNotFoundComponent} from "./components/error/page-not-found/page-not-found.component";

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    PageNotFoundComponent
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    RouterModule,
    HttpClientModule,
    StoreModule.forRoot(appReducers),
    CoreModule
  ],

  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    AuthService,
    CookieService,
  ],

  bootstrap: [AppComponent],
})
export class AppModule {
}
