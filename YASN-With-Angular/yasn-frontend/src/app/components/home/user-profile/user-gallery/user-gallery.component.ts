import {Component, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {Album} from "../../../../shared/models/gallery/Album";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-user-gallery',
  templateUrl: './user-gallery.component.html',
  styleUrls: ['./user-gallery.component.css']
})
export class UserGalleryComponent implements OnInit {

  albums: Array<Album>;

  constructor(private http: HttpRepositoryService) {
  }

  ngOnInit(): void {
    this.http.get<Array<Album>>(EndpointUrls.getUserPicturesAlbums)
      .pipe(take(1))
      .subscribe(value => this.albums = value);
  }

}
