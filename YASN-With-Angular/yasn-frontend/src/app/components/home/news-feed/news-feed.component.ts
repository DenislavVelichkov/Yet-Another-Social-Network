import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";
import {timeConverter} from "../../../core/util/util"
import {UserProfileState} from "../../../core/store/userProfile/state/user-profile.state";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  public newsFeedPosts: Post[];
  private comments: Comment[];
  public showComments: boolean;
  public postAvatar: UserProfileState;
  public postId = 'postId';

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {

  }

  ngOnInit() {

    this.newsService.getAllNewsFeeds()

    this.store.select('userProfile').subscribe(value => {
      this.postAvatar = value;
    })

    this.store.select('newsFeed').subscribe(value =>
      this.newsFeedPosts = value.allWallPosts);
  }

  postCommentPop() {
    this.showComments = !this.showComments;
  }

  likeAPost() {

  }

  convertTime(date: Date): string {
    return timeConverter(date)
  }

}
