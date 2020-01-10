import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home.component";
import {NewsFeedComponent} from "./news-feed/news-feed.component";
import {CreatePostComponent} from "../create-post/create-post.component";


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent,
    CreatePostComponent,
  ],
  imports: [
    CommonModule,
  ],
  exports: [HomeComponent]
})
export class HomeModule { }
