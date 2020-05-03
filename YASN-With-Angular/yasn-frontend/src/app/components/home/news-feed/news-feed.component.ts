import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";
import {AvatarModel} from "../../../core/store/userProfile/AvatarModel";
import {timeConverter} from "../../../core/util/util"

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  private newsFeedPosts: Post[];
  public showComments: boolean;
  public avatar: AvatarModel;

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {
    this.avatar = new AvatarModel()
  }

  ngOnInit() {

    this.newsService.getAllNewsFeeds()

    this.store.select('userProfile').subscribe(value => {
      Object.assign(this.avatar, value)
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
