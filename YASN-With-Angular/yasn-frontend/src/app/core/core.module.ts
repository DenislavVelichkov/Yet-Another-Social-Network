import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from "../components/footer/footer.component";
import {NavbarModule} from "../components/navbar/navbar.module";
import {NavbarComponent} from "../components/navbar/navbar.component";
import {EnvironmentUrlService} from "./http/environment-url.service";
import {HttpRepositoryService} from "./http/http-repository.service";
import {StoreModule} from "@ngrx/store";
import {NewsFeedService} from "./services/news-feed/news-feed.service";
import {appReducers} from "./store/app.reducers";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    FooterComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    NavbarModule,
    StoreModule.forRoot(appReducers),
  ],
  exports: [FooterComponent, NavbarComponent],
})
export class CoreModule {
  /*Ensure one instance of the services*/
  //Add additional services here !
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
