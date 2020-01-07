import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexModule} from './components/index/index.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./core/services/auth.service";
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";
import {HomeComponent} from './components/home/home.component';
import {NewsFeedComponent} from "./components/home/news-feed/news-feed.component";
import {CookieService} from "ngx-cookie-service";
import {AuthInterceptor} from "./core/interceptors/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NewsFeedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule.forRoot(),
    IndexModule.forRoot()
  ],
  providers: [
    AuthService,
    CookieService,
    {
      provide:HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: XhrInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
