import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home.component";
import {NewsFeedComponent} from "./news-feed/news-feed.component";
import {FormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {CreatePostComponent} from "./create-post/create-post.component";
import {PickerModule} from "@ctrl/ngx-emoji-mart";
import {EmojiModule} from "@ctrl/ngx-emoji-mart/ngx-emoji";


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent,
    CreatePostComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    PickerModule,
    EmojiModule
  ],
  exports: [HomeComponent]
})
export class HomeModule {

}
