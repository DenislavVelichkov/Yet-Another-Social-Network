import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home.component";
import {NewsFeedComponent} from "./news-feed/news-feed.component";


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent
  ],
  imports: [
    CommonModule,
  ],
  exports: [HomeComponent]
})
export class HomeModule { }
