import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  private newsFeedPosts: Observable<Post>;

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    //todo remove console log, add state management
    this.newsFeedPosts = this.newsService.getAllNewsFeeds();

    this.newsFeedPosts.subscribe(value => {
      console.log(JSON.stringify(value))
      return value;
    })
  }

}
