import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {endpointUrls} from "../../../shared/common/EndpointURLs";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class NewsFeedService {

  constructor(private httpRepo: HttpRepositoryService) {
  }

  getAllNewsFeeds(): Observable<Object> {
    return this.httpRepo.get(endpointUrls.pullAllNews);
  }
}
