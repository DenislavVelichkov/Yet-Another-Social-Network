import {Component, OnInit} from '@angular/core';
import {HttpRepositoryService} from "../../../../core/http/http-repository.service";
import {EndpointUrls} from "../../../../shared/common/EndpointUrls";
import {Album} from "../../../../shared/models/gallery/Album";
import {take} from "rxjs/operators";
import {UserAuthModel} from "../../../../core/store/authentication/UserAuthModel";
import {AuthService} from "../../../../core/services/authentication/auth.service";

@Component({
  selector: 'app-user-gallery',
  templateUrl: './user-gallery.component.html',
  styleUrls: ['./user-gallery.component.css']
})
export class UserGalleryComponent implements OnInit {
  currentUser: UserAuthModel;
  albums: Array<Album>;

  constructor(private http: HttpRepositoryService,
              private auth: AuthService) {
  }

  ngOnInit(): void {
    this.currentUser = this.auth.getActiveUser();

    this.http.get<Array<Album>>(EndpointUrls.getUserPicturesAlbums + this.currentUser.userProfileId)
      .pipe(take(1))
      .subscribe(value => {
        return this.albums = value;
      }, error => console.log(new Error(error)));
  }

}
