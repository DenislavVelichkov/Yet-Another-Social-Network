import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home.component";
import {NewsFeedComponent} from "./news-feed/news-feed.component";
import {FormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {CreatePostComponent} from "./create-post/create-post.component";
import {PickerModule} from "@ctrl/ngx-emoji-mart";
import {EmojiModule} from "@ctrl/ngx-emoji-mart/ngx-emoji";
import {MatCardModule} from "@angular/material/card";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {CreateCommentComponent} from './create-comment/create-comment.component';


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent,
    CreatePostComponent,
    CreateCommentComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    PickerModule,
    EmojiModule,
    MatCardModule,
    MatProgressBarModule,
    MatIconModule,
    MatButtonModule
  ],
  exports: [HomeComponent]
})
export class HomeModule {

}
