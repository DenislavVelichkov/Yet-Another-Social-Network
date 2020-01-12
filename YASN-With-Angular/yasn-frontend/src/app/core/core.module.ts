import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from "../components/footer/footer.component";
import {NavbarModule} from "../components/navbar/navbar.module";
import {NavbarComponent} from "../components/navbar/navbar.component";
import {EnvironmentUrlService} from "./http/environment-url.service";
import {HttpRepositoryService} from "./http/http-repository.service";
import {ErrorComponent} from '../components/error/error.component';
import {StoreModule} from "@ngrx/store";
import {NewsFeedService} from "./services/news-feed/news-feed.service";
import {appReducers} from "./store/app.reducers";

@NgModule({
  declarations: [
    FooterComponent,
    ErrorComponent
  ],
  imports: [
    CommonModule,
    NavbarModule,
    StoreModule.forRoot(appReducers)
  ],
  exports: [FooterComponent, NavbarComponent]
})
export class CoreModule {
  /*Ensure one instance of the services*/
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: CoreModule,
      providers: [
        EnvironmentUrlService,
        HttpRepositoryService,
        NewsFeedService
      ]
    };
  }
}
