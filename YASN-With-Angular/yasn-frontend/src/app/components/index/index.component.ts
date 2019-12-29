import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
 title: string;

  constructor() { 
    this.title = 'Log In or Register';
  }

  ngOnInit() {
  }

}
