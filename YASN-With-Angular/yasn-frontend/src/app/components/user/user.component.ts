import { Component, OnInit } from '@angular/core';
import { MatDatepickerModule } from '@angular/material';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  private title: string;

  constructor() { 
    this.title = "Log in or Register"
  }

  ngOnInit() {
  }

}
