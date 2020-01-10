import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit, AfterViewInit {

  constructor() {
  }

  ngOnInit() {


  }

  ngAfterViewInit(): void {
    // @ts-ignore
    $("#comment-text").emojioneArea({
      buttonTitle: "Use the TAB key to insert emoji faster",
      recentEmojies: true,
      pickerPosition: "bottom",
      filtersPosition: "top",
      hidePickerOnBlur: true,
      search: false
    });
  }
}
