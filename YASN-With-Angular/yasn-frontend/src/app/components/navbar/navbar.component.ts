import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {AppState} from "../../core/store/app.state";
import {take, takeLast} from "rxjs/operators";
import {AuthState} from "../../core/store/authentication/state/auth.state";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {
  private authState: Observable<AuthState[]>;
  private isAuthenticated: boolean;

  constructor(private store: Store<AppState>) {}

  ngOnInit() {
    this.authState = this.store.select('auth').pipe(take(1));
    this.isAuthenticated = this.authState.pipe(takeLast(1))['isAuthenticated'];
  }
}
