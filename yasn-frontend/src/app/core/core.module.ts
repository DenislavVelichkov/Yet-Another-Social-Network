import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from '../components/footer/footer.component';
import {EnvironmentUrlService} from './http/environment-url.service';
import {HttpRepositoryService} from "./http/http-repository.service";
import {NewsFeedService} from "./services/news-feed/news-feed.service";
import {RouterModule} from "@angular/router";
import {NotificationService} from "./services/notification/notification.service";
import {PickerModule} from "@ctrl/ngx-emoji-mart";
import {UserLoginComponent} from "../components/user/user-login/user-login.component";
import {UserRegisterComponent} from "../components/user/user-register/user-register.component";
import {IndexComponent} from "../components/index/index.component";
import {FormsModule} from "@angular/forms";
import {MatBadgeModule} from "@angular/material/badge";
import {UnAuthorizedNavbarComponent} from "../components/navbar/un-authorized-navbar/un-authorized-navbar.component";
import {HomeComponent} from "../components/home/home.component";
import {NewsFeedComponent} from "../components/home/news-feed/news-feed.component";
import {CreatePostComponent} from "../components/home/create-post/create-post.component";
import {CreateCommentComponent} from "../components/home/create-comment/create-comment.component";
import {UserProfileComponent} from "../components/home/user-profile/user-profile.component";
import {UserProfileHeaderComponent} from "../components/home/user-profile/user-profile-header/user-profile-header.component";
import {UserGalleryComponent} from "../components/home/user-profile/user-gallery/user-gallery.component";
import {UserProfileBodyComponent} from "../components/home/user-profile/user-profile-body/user-profile-body.component";
import {UserFriendsComponent} from "../components/home/user-profile/user-friends/user-friends.component";
import {EditUserProfileComponent} from "../components/home/user-profile/edit-user-profile/edit-user-profile.component";
import {AuthorizedNavbarComponent} from "../components/navbar/authorized-navbar/authorized-navbar.component";
import {EmojiModule} from "@ctrl/ngx-emoji-mart/ngx-emoji";
import {MatCardModule} from "@angular/material/card";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {CustomSuccessSnackbarComponent} from "../components/custom-snackbar/success-snackbar/custom-success-snackbar.component";
import {SearchBarComponent} from "../components/search-bar/search-bar.component";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {ErrorSnackbarComponent} from "../components/custom-snackbar/error-snackbar/error-snackbar.component";
import {TooltipDirective} from "./directives/tooltip.directive";
import {EditUserGalleryComponent} from "../components/home/user-profile/edit-user-gallery/edit-user-gallery.component";

@NgModule({
  declarations: [
    FooterComponent,
    UserLoginComponent,
    UserRegisterComponent,
    IndexComponent,
    UnAuthorizedNavbarComponent,
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
    AuthorizedNavbarComponent,
    CustomSuccessSnackbarComponent,
    ErrorSnackbarComponent,
    SearchBarComponent,
    TooltipDirective,
    EditUserGalleryComponent,
  ],
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        PickerModule,
        MatBadgeModule,
        EmojiModule,
        MatCardModule,
        MatProgressBarModule,
        MatIconModule,
        MatButtonModule,
        MatSnackBarModule,
        MatDialogModule,
    ],
  exports: [
    IndexComponent,
    HomeComponent,
    FooterComponent,
    AuthorizedNavbarComponent,
    UnAuthorizedNavbarComponent,
    CustomSuccessSnackbarComponent,
    ErrorSnackbarComponent,
    SearchBarComponent,
    ],
})
export class CoreModule {
  /*Ensure one instance of the services*/

  // Add additional services here !
  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        EnvironmentUrlService,
        HttpRepositoryService,
        NewsFeedService,
        NotificationService,
        {provide: MatDialogRef, useValue: {}}
      ]
    };
  }
}
