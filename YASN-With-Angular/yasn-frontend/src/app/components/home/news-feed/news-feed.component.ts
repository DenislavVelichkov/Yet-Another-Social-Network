import {Component, OnInit} from '@angular/core';
import {throwError} from "rxjs";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {NewsFeedService} from "../../../core/services/news-feed/news-feed.service";
import {Post} from "../../../core/store/post/Post";
import {DisplayAllPostsAction} from "../../../core/store/post/actions/display-all-posts.action";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {
  private newsFeedPosts: Post[];

  constructor(private newsService: NewsFeedService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.newsService.getAllNewsFeeds()
      .pipe(take(1))
      .subscribe((value: Post[]) => {
        this.store.dispatch(new DisplayAllPostsAction({allWallPosts: value, loading: true}))
      }, error => throwError(error));

    this.store.select('newsFeed').subscribe(value => this.newsFeedPosts = value.allWallPosts);
  }

}
