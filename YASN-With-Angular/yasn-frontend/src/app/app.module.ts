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
import {appReducer} from "./core/store/app.reducer";
import {CommonModule} from "@angular/common";
import {CoreModule} from "./core/core.module";
import {PageNotFoundComponent} from "./components/error/page-not-found/page-not-found.component";
import {LoadingComponent} from './components/loading/loading.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    PageNotFoundComponent,
    LoadingComponent,
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    RouterModule,
    HttpClientModule,
    StoreModule.forRoot(appReducer),
    CoreModule.forRoot(),
    BrowserAnimationsModule,
    MatProgressSpinnerModule]
  ,

  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    AuthService,
    CookieService,
  ],

  bootstrap: [AppComponent],
  exports: [
  ]
})

export class AppModule {
}
