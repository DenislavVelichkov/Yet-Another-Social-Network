import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";
import {UserAuthModel} from "../../../core/store/authentication/UserAuthModel";
import {PostBindingModel} from "../../../shared/models/post/PostBindingModel";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {NotificationService} from "../../../core/services/notification/notification.service";
import {StopLoadingAction} from "../../../core/store/loading/actions/stop-loading.action";
import {StartLoadingAction} from "../../../core/store/loading/actions/start-loading.action";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  public postModel: PostBindingModel;
  public showEmojies: boolean;
  public emojiIcon: string;
  public showPrivacyMenu: boolean;
  public showUploadPhotoMenu: boolean;
  public tagFriendsMenu: boolean;
  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;
  public files: Array<File> = [];
  private activeProfile: UserAuthModel;

  constructor(private httpRepo: HttpRepositoryService,
              private store: Store<AppState>,
              private newsFeedService: NewsFeedService,
              private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.emojiIcon = 'fas fa-smile';
    this.postModel = new PostBindingModel();
    this.store.select('auth')
      .subscribe(value => this.activeProfile = value.activeUser);
  }

  createPost() {
    this.store.dispatch(new StartLoadingAction({loading: true}));

    this.newsFeedService.createPost(
      this.postModel,
      JSON.parse(localStorage.getItem("activeUser"))._userProfileId,
      this.files);

   /* this.notificationService.createNotificationOnNewPost(
      JSON.parse(localStorage.getItem("activeUser"))._userProfileId);*/

    this.store.dispatch(new StopLoadingAction({loading: false}));
  }

  onEmojiClick() {
    if (this.showPrivacyMenu) {
      this.showPrivacyMenu = !this.showPrivacyMenu;
    }

    if (this.showUploadPhotoMenu) {
      this.showUploadPhotoMenu = !this.showUploadPhotoMenu;
    }

    this.showEmojies = !this.showEmojies;
    if (this.showEmojies) {
      this.emojiIcon = 'fa fa-times';
    } else {
      this.emojiIcon = 'fas fa-smile';
    }
  }

  addEmoji($ev) {
    let message = this.postModel.postContent
    const selectedEmoji = $ev.emoji.native
    message ? message += selectedEmoji : message = selectedEmoji;

    this.postModel.postContent = message;
  }

  onPrivacyClick() {
    if (this.showEmojies) {
      this.showEmojies = !this.showEmojies;
      this.emojiIcon = 'fas fa-smile';

    }

    if (this.showUploadPhotoMenu) {
      this.showUploadPhotoMenu = !this.showUploadPhotoMenu;

    }
    this.showPrivacyMenu = !this.showPrivacyMenu;
  }

  onUploadPhotoClick() {
    if (this.showEmojies) {
      this.showEmojies = !this.showEmojies;
      this.emojiIcon = 'fas fa-smile';

    }
    if (this.showPrivacyMenu) {
      this.showPrivacyMenu = !this.showPrivacyMenu;
    }

    this.showUploadPhotoMenu = !this.showUploadPhotoMenu
  }

  onUploadPic() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (let index = 0; index < fileUpload.files.length; index++) {
        const file = fileUpload.files[index];
        file.inProgress = false;
        file.progress = 0;
        this.files.push(file);
      }
    };

    fileUpload.click();
    this.onUploadPhotoClick();
  }

}
