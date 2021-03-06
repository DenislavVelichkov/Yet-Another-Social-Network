import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";
import {timeAgoConverter} from "../../../core/util/util"
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {LikeAPost} from "../../../core/store/post/LikeAPost";
import {LikeAPostAction} from "../../../core/store/post/actions/like-a-post.action";
import {UnlikeAPostAction} from "../../../core/store/post/actions/unlike-a-post.action";
import {Subscription} from "rxjs";
import {WebsocketService} from "../../../core/services/websocket/websocket.service";
import {CreatePost} from "../../../core/store/post/actions/create-post.action";
import {CommentOnPostAction} from "../../../core/store/post/actions/comment-on-post.action";
import {PostComment} from "../../../core/store/post/PostComment";
import {AuthService} from "../../../core/services/authentication/auth.service";
import {NotificationService} from "../../../core/services/notification/notification.service";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit, OnDestroy {

  public activeUserInfo: UserProfileState;

  public newsFeedPosts: Post[];

  private postSubscription$: Subscription;

  private commentsSubscription$: Subscription;

  private doLikeSub$: Subscription;

  private doUnlikeSub$: Subscription;

  constructor(private newsService: NewsFeedService,
              private store$: Store<AppState>,
              private http: HttpRepositoryService,
              private websocketService: WebsocketService,
              private auth: AuthService,
              private notificationService: NotificationService) {
  }

  ngOnInit() {

    this.store$.select('userProfile').subscribe(value => {
      this.activeUserInfo = value;
    })

    let currentUserProfileId = this.auth.getActiveUser().userProfileId;

    this.newsService.getAllNewsFeeds(currentUserProfileId);

    this.store$.select('newsFeed').subscribe(value => {
      this.newsFeedPosts = value.allWallPosts;
    });

    this.postSubscription$ = this.websocketService.getPostsData().subscribe((post: string) => {
      let newPost: Post = Object.assign({}, JSON.parse(post));
      this.store$.dispatch(new CreatePost({post: newPost}));
    }, error => console.log(new Error(error)));

    this.commentsSubscription$ = this.websocketService.getCommentsData().subscribe((data: string) => {
      let newComment: PostComment = Object.assign({}, JSON.parse(data));

      this.store$.dispatch(new CommentOnPostAction({comment: newComment}));
    }, error => console.log(new Error(error)));

   this.doLikeSub$ = this.websocketService.doLike().subscribe((data: string) => {
      this.store$.dispatch(new LikeAPostAction(JSON.parse(data)));
    }, error => console.log(new Error(error)));

   this.doUnlikeSub$ = this.websocketService.doUnlike().subscribe((data: string) => {
      this.store$.dispatch(new UnlikeAPostAction(JSON.parse(data)));
    }, error => console.log(new Error(error)));

  }

  likeAPost(postId: string, isPostAlreadyLiked: boolean) {
    let likeModel: LikeAPost = {
      postId: postId,
      userProfileId: this.activeUserInfo.userProfileId
    };

    if (isPostAlreadyLiked) {
      this.http.create(EndpointUrls.unLikeAPost, likeModel)
        .pipe(take(1))
        .subscribe(() => {
        }, error => console.log(new Error(error)));
    } else {
      this.http.create(EndpointUrls.likeAPost, likeModel)
        .pipe(take(1))
        .subscribe(() => {
        }, error => console.log(new Error(error)));
    }

  }

  convertTime(date: Date): string {
    return timeAgoConverter(date);
  }

  ngOnDestroy(): void {
    this.postSubscription$.unsubscribe();
    this.commentsSubscription$.unsubscribe();
    this.doLikeSub$.unsubscribe();
    this.doUnlikeSub$.unsubscribe();
  }

}
