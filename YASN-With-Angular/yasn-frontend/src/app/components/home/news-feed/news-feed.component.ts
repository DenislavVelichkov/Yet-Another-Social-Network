import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  private newsFeedPosts: Observable<Object>;

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.newsFeedPosts = this.newsService.getAllNewsFeeds();

    this.newsFeedPosts.subscribe(value => {
      console.log(JSON.stringify(value))
      return value;
    })
  }

}
