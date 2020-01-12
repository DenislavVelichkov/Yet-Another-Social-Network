import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Post} from "../../store/post/Post";

@Injectable()
export class NewsFeedService {

  constructor() {
  }

  getAllNewsFeeds(): Observable<Post[]> {
    return undefined;
  }
}
