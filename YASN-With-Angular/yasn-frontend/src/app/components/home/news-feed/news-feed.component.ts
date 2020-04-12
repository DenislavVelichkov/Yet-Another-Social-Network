import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Post} from "../../../core/store/post/Post";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  private newsFeedPosts: Observable<Post[]>;

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
   /* this.newsService.getAllNewsFeeds()
      .subscribe(() => {
        this.newsFeedPosts =
          this.store.select(state => state.posts.all);
      });*/
  }

}
