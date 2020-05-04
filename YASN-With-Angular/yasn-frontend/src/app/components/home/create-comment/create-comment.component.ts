import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";
import {CommentBindingModel} from "../../../shared/models/comment/CommentBindingModel";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";
import {UserAuthModel} from "../../../core/store/authentication/UserAuthModel";

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {
  private userProfile: UserAuthModel;
  public commentAvatar: UserProfileState;
  public commentModel: CommentBindingModel;

  @ViewChild('uploadCommentPhoto', {static: false}) uploadCommentPhoto: ElementRef;
  public files: any;

  @Input('postId') postId: string;

  constructor(private store: Store<AppState>,
              private newsFeedService: NewsFeedService) {
    this.commentModel = new CommentBindingModel();

  }

  ngOnInit(): void {

    this.store.select('userProfile').subscribe(value => {
      this.commentAvatar = value;
    })

    this.store.select('auth').subscribe(value => {
      this.userProfile = value.activeUser;
    })

  }

  postComment(): void {
    this.commentModel.commentOnPostId = this.postId;

    const fileUpload = this.uploadCommentPhoto.nativeElement;
    let files: Array<File> = [];

    for (let file of fileUpload.files) {
      files.push(file)
    }

    this.newsFeedService.createComment(
      this.userProfile.userProfileId,
      this.commentModel,
      files)
  }
}
