import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit, AfterViewInit {
  private activeProfile: any;

  constructor(private httpRepo: HttpRepositoryService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.select('auth')
      .subscribe(value => this.activeProfile = value.activeUser);
  }

  ngAfterViewInit(): void {
    // @ts-ignore
    //JQuery activate emote icons.
    $("#comment-text").emojioneArea({
      buttonTitle: "Use the TAB key to insert emoji faster",
      recentEmojies: true,
      pickerPosition: "bottom",
      filtersPosition: "top",
      hidePickerOnBlur: true,
      search: false
    });
  }

  createPost(post: FormData) {
    this.httpRepo.createUser("/home/news-feed/post", post)
  }
}
