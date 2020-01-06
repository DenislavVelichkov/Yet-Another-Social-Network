import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexModule} from './components/index/index.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";
import {NewsFeedComponent} from './components/home/news-feed/news-feed.component';
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./core/services/auth.service";

@NgModule({
  declarations: [
    AppComponent,
    NewsFeedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule.forRoot(),
    IndexModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
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
