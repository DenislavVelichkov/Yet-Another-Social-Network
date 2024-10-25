import {Component, Inject} from '@angular/core';
// Update the import to use the new MatSnackBarData
import {MAT_SNACK_BAR_DATA} from "@angular/material/snack-bar";

@Component({
  selector: 'app-custom-success-snackbar',
  templateUrl: './custom-success-snackbar.component.html',
  styleUrls: ['./custom-success-snackbar.component.css']
})
export class CustomSuccessSnackbarComponent {

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: string) { }

}
