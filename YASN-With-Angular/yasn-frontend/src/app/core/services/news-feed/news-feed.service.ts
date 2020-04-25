import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {endpointUrls} from "../../../shared/common/EndpointURLs";
import {Observable} from "rxjs";
import {Post} from "../../store/post/Post";

@Injectable({providedIn: "root"})
export class NewsFeedService {

  constructor(private httpRepo: HttpRepositoryService) {
  }

  getAllNewsFeeds(): Observable<Post> {
    return this.httpRepo.get<Post>(endpointUrls.pullAllNews);
  }
}
