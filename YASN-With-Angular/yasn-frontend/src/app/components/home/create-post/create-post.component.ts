import {Component, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";
import {UserAuthModel} from "../../../core/store/authentication/UserAuthModel";
import {endpointUrls} from "../../../shared/common/EndpointURLs";
import {PostBindingModel} from "../../../shared/models/post/PostBindingModel";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  private activeProfile: UserAuthModel;
  public postModel: PostBindingModel;
  public showEmojies: boolean;
  public emojiIcon: string;
  public showPrivacyMenu: boolean;
  public showUploadPhotoMenu: boolean;
  public showLocationMenu: boolean;
  public tagFriendsMenu: boolean;

  constructor(private httpRepo: HttpRepositoryService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.emojiIcon = 'fas fa-smile';
    this.postModel = new PostBindingModel();
    this.store.select('auth')
      .subscribe(value => this.activeProfile = value.activeUser);
  }

  createPost() {
    let formData = new FormData();

    console.log(JSON.stringify(this.postModel))

    let postBlobModel = new Blob(
      [JSON.stringify(this.postModel)],
      {type: 'application/json'}
    );

    formData.append("postOwnerId", this.activeProfile.userProfileId);
    formData.append("post", postBlobModel);

    console.log(formData)

    this.httpRepo.create(endpointUrls.postToPublicWall, formData)
      .subscribe(post => {
        console.log(JSON.stringify(post))
      });
  }

  onEmojiClick() {
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
}
