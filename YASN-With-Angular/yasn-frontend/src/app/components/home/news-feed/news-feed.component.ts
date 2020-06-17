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
import {Subscription, throwError} from "rxjs";
import {WebsocketService} from "../../../core/services/websocket/websocket.service";
import {CreatePost} from "../../../core/store/post/actions/create-post.action";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit, OnDestroy {

  public userProfileInfo: UserProfileState;

  public showComments: boolean;

  public newsFeedPosts: Post[];

  private subscription: Subscription;

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>,
              private http: HttpRepositoryService,
              private websocketService: WebsocketService) {
  }

  ngOnInit() {

    this.store.select('userProfile')
      .subscribe(value => {
        this.userProfileInfo = value;
      })

    let currentUserProfileId =
      JSON.parse(localStorage.getItem('activeUser'))._userProfileId;

    this.newsService.getAllNewsFeeds(currentUserProfileId);

    this.subscription = this.store.select('newsFeed').subscribe(value =>{
      this.newsFeedPosts = value.allWallPosts;
    });

    this.websocketService.connect("/new-post-created");
    this.websocketService.getData().subscribe((post: string) => {
      let obj: Post = Object.assign({}, JSON.parse(post));

      console.log(obj);

      if (post) {
        this.store.dispatch(new CreatePost({post: [post]}));
      }

    });
  }

  postCommentPop() {
    this.showComments = !this.showComments;
  }

  likeAPost(postId: string, isPostAlreadyLiked: boolean) {
    let likeModel: LikeAPost = {
      postId: postId,
      userProfileId: this.userProfileInfo.userProfileId
    };

    if (isPostAlreadyLiked) {
      this.http.create(EndpointUrls.unLikeAPost, likeModel)
        .pipe(take(1))
        .subscribe(() => this.store.dispatch(
            new UnlikeAPostAction(likeModel)), error => throwError(error));

    } else {
      this.http.create(EndpointUrls.likeAPost, likeModel)
        .pipe(take(1))
        .subscribe(() => this.store.dispatch(
            new LikeAPostAction(likeModel)), error => throwError(error));
    }

  }

  convertTime(date: Date): string {
    return timeAgoConverter(date);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.websocketService.disconnect();
  }

}
