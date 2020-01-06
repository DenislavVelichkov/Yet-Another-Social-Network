import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexModule} from './components/index/index.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./core/services/auth.service";
import {XhrInterceptor} from "./core/interceptors/xhr.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule.forRoot(),
    IndexModule
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
