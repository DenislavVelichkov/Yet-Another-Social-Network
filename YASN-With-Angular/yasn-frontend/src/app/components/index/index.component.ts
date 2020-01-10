import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
 private _showLoginPage: boolean;

  constructor() {
  }

  ngOnInit() {
  }

  get showLoginPage(): boolean {
    return this._showLoginPage;
  }

  set showLoginPage(value: boolean) {
    this._showLoginPage = value;
  }
}
