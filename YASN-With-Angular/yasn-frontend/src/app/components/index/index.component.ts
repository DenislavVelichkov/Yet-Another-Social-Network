import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../core/store/app.state";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  private isAuthenticated: boolean;
  private isRegistered: boolean;
  private isLoading: boolean;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.select('auth').subscribe(value => {
      this.isRegistered = value.isRegistered;
      this.isAuthenticated = value.isAuthenticated;
      this.isLoading = value.loading;
    });
  }
}
