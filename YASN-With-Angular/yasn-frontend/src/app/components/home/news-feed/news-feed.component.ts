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

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  public newsFeedPosts: Post[];
  private comments: Comment[];
  public showComments: boolean;
  public userProfileInfo: UserProfileState;
  public postId = 'postId';

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>,
              private http: HttpRepositoryService) {

  }

  ngOnInit() {

    this.newsService.getAllNewsFeeds()

    this.store.select('userProfile').subscribe(value => {
      this.userProfileInfo = value;
    })

    this.store.select('newsFeed').subscribe(value =>
      this.newsFeedPosts = value.allWallPosts);
  }

  postCommentPop() {
    this.showComments = !this.showComments;
  }

  likeAPost(postId: string) {
    let likeAPost: LikeAPost = {postId: postId, userProfileId: this.userProfileInfo.userProfileId}
    this.http.create(EndpointUrls.likeAPost, likeAPost)
             .pipe(take(1))
             .subscribe(data => this.store.dispatch(new LikeAPostAction(likeAPost)));
  }

  convertTime(date: Date): string {
    return timeConverter(date)
  }

}
