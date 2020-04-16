import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {AppState} from "../../core/store/app.state";

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {
  private loading: boolean;

  constructor(private store: Store<AppState>) { }

  ngOnInit(): void {
    this.store.select('auth').subscribe(value => this.loading = value.loading)
  }

}
