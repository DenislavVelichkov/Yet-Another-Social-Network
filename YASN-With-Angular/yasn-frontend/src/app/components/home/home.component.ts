import {Component, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {Store} from "@ngrx/store";
import {AppState} from "../../core/store/app.state";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private isAuthenticated: boolean;

  constructor(private title: Title,
              private store: Store<AppState>) {

  }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Home');
    this.store.select('auth').subscribe(value => {
      this.isAuthenticated = value.isAuthenticated
    });
  }

}
