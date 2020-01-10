import {Component, OnInit} from '@angular/core';
import {UserRegisterBindingModel} from 'src/app/shared/models/user/UserRegisterBindingModel';
import {UserService} from 'src/app/core/services/user/user.service';
import {Title} from "@angular/platform-browser";
import {Router} from '@angular/router';
import {IndexComponent} from "../../index/index.component";

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css', '../../index/index.component.css']
})
export class UserRegisterComponent implements OnInit {
  private userRegisterBindingModel: UserRegisterBindingModel;
  private isUserRegistered: boolean;
  private errors: Array<Object>;

  constructor(private userService: UserService,
              private router: Router,
              private title: Title) { }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Log In or Register');
    this.userRegisterBindingModel = new UserRegisterBindingModel();
  }

  onSubmit() {
    let formData = new FormData();

    let userBlobModel = new Blob(
      [JSON.stringify(this.userRegisterBindingModel)],
      {type: 'application/json'}
    );

    formData.append("registerModel", userBlobModel);

    this.userService.registerUser(formData).subscribe(data => {
      this.isUserRegistered = data['isUserRegistered'];

      if (!this.isUserRegistered) {
        this.userRegisterBindingModel = data['rejectedModel'];
        this.errors = [...data['errors']];
        this.errors.forEach(error => alert(error['defaultMessage']));
        this.router.navigate(['/']);
      } else {
        IndexComponent.prototype.showLoginPage = this.isUserRegistered;
        this.router.navigate(['user/login']);
      }
    });

  }
}
