import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from "../components/footer/footer.component";
import {NavbarModule} from "../components/navbar/navbar.module";
import {NavbarComponent} from "../components/navbar/navbar.component";
import {EnvironmentUrlService} from "./http/environment-url.service";
import {HttpRepositoryService} from "./http/http-repository.service";
import {NewsFeedService} from "./services/news-feed/news-feed.service";
import {RouterModule} from "@angular/router";
import {IndexModule} from "../components/index/index.module";
import {HomeModule} from "../components/home/home.module";

@NgModule({
  declarations: [
    FooterComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    IndexModule,
    HomeModule,
    NavbarModule,
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
