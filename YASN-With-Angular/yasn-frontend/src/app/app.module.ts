import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FooterComponent} from './shared/components/footer/footer.component';
import {NavbarModule} from './shared/components/navbar/navbar.module';
import {IndexModule} from './components/index/index.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthService} from "./core/services/auth.service";
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";
import {NewsFeedComponent} from './components/home/news-feed/news-feed.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NewsFeedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NavbarModule,
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
