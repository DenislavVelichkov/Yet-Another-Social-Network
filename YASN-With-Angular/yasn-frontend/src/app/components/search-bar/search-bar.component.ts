import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchQuery: string;

  constructor(public dialogRef: MatDialogRef<SearchBarComponent>) {
  }

  ngOnInit(): void {
  }

  onSearch() {
    console.log(this.searchQuery)
  }

}
