import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";
import {timeConverter} from "../../../core/util/util"
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {take} from "rxjs/operators";
import {LikeAPost} from "../../../core/store/post/LikeAPost";
import {LikeAPostAction} from "../../../core/store/post/actions/like-a-post.action";
import {UnlikeAPostAction} from "../../../core/store/post/actions/unlike-a-post.action";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  public userProfileInfo: UserProfileState;
  public showComments: boolean;
  public postId = 'postId';
  public newsFeedPosts: Post[];

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>,
              private http: HttpRepositoryService) {
  }

  ngOnInit() {

    this.store.select('userProfile')
      .subscribe(value => {
        this.userProfileInfo = value;
      })

    let currentUserProfileId =
      JSON.parse(localStorage.getItem('activeUser'))._userProfileId;

    this.newsService.getAllNewsFeeds(currentUserProfileId)

    this.store.select('newsFeed').subscribe(value =>
      this.newsFeedPosts = value.allWallPosts);
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
        .subscribe(data => this.store.dispatch(new UnlikeAPostAction(likeModel)));

    } else {
      this.http.create(EndpointUrls.likeAPost, likeModel)
        .pipe(take(1))
        .subscribe(data => this.store.dispatch(new LikeAPostAction(likeModel)));
    }


  }

  convertTime(date: Date): string {
    return timeConverter(date)
  }

}
