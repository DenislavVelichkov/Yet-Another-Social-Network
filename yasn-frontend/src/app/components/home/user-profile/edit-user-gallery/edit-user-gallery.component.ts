import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-edit-user-gallery',
  templateUrl: './edit-user-gallery.component.html',
  styleUrls: ['./edit-user-gallery.component.css']
})
export class EditUserGalleryComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditUserGalleryComponent>) { }

  ngOnInit(): void {
  }

}
