import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {endpointUrls} from "../../../shared/common/EndpointURLs";
import {map, take} from "rxjs/operators";

@Injectable({providedIn: "root"})
export class NewsFeedService {

  constructor(private httpRepo: HttpRepositoryService) {
  }

  getAllNewsFeeds() {
    let collection: Object = [];
    this.httpRepo.get(endpointUrls.pullAllNews).pipe(take(1), map(value => {
      collection = value;

      console.log(collection)
    }));

    return collection;
  }
}
