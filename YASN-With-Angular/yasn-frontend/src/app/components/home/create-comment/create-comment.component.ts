import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";
import {CommentBindingModel} from "../../../shared/models/comment/CommentBindingModel";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";
import {UserAuthModel} from "../../../core/store/authentication/UserAuthModel";
import {StartLoadingAction} from "../../../core/store/loading/actions/start-loading.action";
import {StopLoadingAction} from "../../../core/store/loading/actions/stop-loading.action";
import {AuthService} from "../../../core/services/authentication/auth.service";

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {

  public commentAvatar: UserProfileState;

  public commentModel: CommentBindingModel;

  private activeProfile: UserAuthModel;

  public files: any;

  @ViewChild('uploadCommentPhoto', {static: false}) uploadCommentPhoto: ElementRef;

  @Input('postId') postId: string;

  constructor(private store$: Store<AppState>,
              private newsFeedService: NewsFeedService,
              private auth: AuthService) {
    this.commentModel = new CommentBindingModel();
  }

  ngOnInit(): void {
    this.activeProfile = this.auth.getActiveUser();

    this.store$.select('userProfile').subscribe(value => {
      this.commentAvatar = value;
    })

  }

  postComment(): void {
    this.commentModel.commentOnPostId = this.postId;
    this.store$.dispatch(new StartLoadingAction({loading: true}));

    const fileUpload = this.uploadCommentPhoto.nativeElement;
    let files: Array<File> = [];

    for (let file of fileUpload.files) {
      files.push(file)
    }

    let userId = JSON.parse(localStorage.getItem('activeUser'))._userProfileId;

    this.newsFeedService.createComment(
      userId,
      this.commentModel,
      files)

    this.store$.dispatch(new StopLoadingAction({loading: false}));
  }
}
