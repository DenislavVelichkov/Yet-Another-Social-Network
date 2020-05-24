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
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserProfileHeaderComponent} from './user-profile/user-profile-header/user-profile-header.component';
import {UserGalleryComponent} from './user-profile/user-gallery/user-gallery.component';
import {UserProfileBodyComponent} from './user-profile/user-profile-body/user-profile-body.component';
import {UserFriendsComponent} from './user-profile/user-friends/user-friends.component';
import {EditUserProfileComponent} from './user-profile/edit-user-profile/edit-user-profile.component';


@NgModule({
  declarations: [
    HomeComponent,
    NewsFeedComponent,
    CreatePostComponent,
    CreateCommentComponent,
    UserProfileComponent,
    UserProfileHeaderComponent,
    UserGalleryComponent,
    UserProfileBodyComponent,
    UserFriendsComponent,
    EditUserProfileComponent,
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
