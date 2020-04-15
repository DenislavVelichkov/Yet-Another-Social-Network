import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../core/store/app.state";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {
  private isAuthenticated: boolean;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.select('auth')
      .subscribe(value => {
        this.isAuthenticated = value.isAuthenticated
      });
  }
}
