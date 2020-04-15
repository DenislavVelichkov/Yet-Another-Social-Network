import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home.component";
import {NewsFeedComponent} from "./news-feed/news-feed.component";
import {FormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {CreatePostComponent} from "./create-post/create-post.component";


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent,
    CreatePostComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  exports: [HomeComponent]
})
export class HomeModule {

}
