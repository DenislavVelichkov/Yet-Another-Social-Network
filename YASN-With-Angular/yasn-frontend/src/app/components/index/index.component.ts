import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {take, takeLast} from "rxjs/operators";
import {AppState} from "../../core/store/app.state";
import {AuthState} from "../../core/store/authentication/state/auth.state";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})

export class IndexComponent implements OnInit {
  private authState: Observable<AuthState[]>;
  private isAuthenticated: boolean;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.authState = this.store.select('auth').pipe(take(1));
    this.isAuthenticated = this.authState.pipe(takeLast(1))['isAuthenticated'];
  }

}
