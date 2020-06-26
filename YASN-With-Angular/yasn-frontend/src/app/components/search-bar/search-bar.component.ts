import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {HttpRepositoryService} from "../../core/http/http-repository.service";
import {EndpointUrls} from "../../shared/common/EndpointUrls";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit, OnDestroy {

  searchQuery: string;

  searchResult: any;

  searchSubscription: Subscription;

  constructor(public dialogRef: MatDialogRef<SearchBarComponent>,
              private http: HttpRepositoryService) {
  }

  ngOnInit(): void {
  }

  onSearch() {
    this.searchSubscription =
      this.http.get(EndpointUrls.searchProfile + this.searchQuery)
        .subscribe((data: any) => {
          this.searchResult = data.searchResult;
        });
  }

  closeDialog() {
    this.dialogRef.close()
  }

  ngOnDestroy(): void {
    this.searchSubscription.unsubscribe();
    this.dialogRef.close()
  }

}
