import {Component, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // private isLoggedIn: boolean;

  constructor(private title: Title,
              /*private store: Store<AppState>*/) {

  }

  ngOnInit() {
    // this.store.select('auth')
    //   .pipe(take(1),
    //     map(value => this.isLoggedIn = value.isLoggedIn));
  }
}
