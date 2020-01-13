import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../core/http/http-repository.service";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit, AfterViewInit {
  private activeProfileId: string;

  constructor(private httpRepo: HttpRepositoryService) {
    this.activeProfileId = localStorage.getItem('userProfileId');
  }

  ngOnInit() {

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
    this.httpRepo.create("/home/news-feed/post", post)
  }
}
