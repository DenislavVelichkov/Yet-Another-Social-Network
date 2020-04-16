import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/authentication/auth.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../core/store/app.state";
import {HttpRepositoryService} from "../../../core/http/http-repository.service";
import {throwError} from "rxjs";
import {UpdateAvatarAction} from "../../../core/store/userProfile/actions/update-avatar.action";

@Component({
  selector: 'app-authorized-navbar',
  templateUrl: './authorized-navbar.component.html',
  styleUrls: ['./authorized-navbar.component.css', '../navbar.component.css']
})
export class AuthorizedNavbarComponent implements OnInit {
  private profilePictureUrl: string;
  private profileId: string;
  private userFullName: string;

  constructor(private auth: AuthService,
              private store: Store<AppState>,
              private httpRepo: HttpRepositoryService) {
  }

  ngOnInit() {
    this.store.select('auth').subscribe(value => {
      this.profileId = value.activeUser.userProfileId;
    })

    this.httpRepo.get(`/api/user-profile/${this.profileId}`).subscribe(value => {
      this.store.dispatch(new UpdateAvatarAction(value))
    }, error => throwError(error))

    this.store.select('userProfile').subscribe(value => {
      this.userFullName = value.userFullName;
      this.profilePictureUrl = value.avatarPictureUrl;
    })

  }

  logout() {
    this.auth.logout();
  }

}
