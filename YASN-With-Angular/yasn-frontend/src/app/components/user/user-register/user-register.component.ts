import {Component, OnInit} from '@angular/core';
import {UserRegisterBindingModel} from 'src/app/shared/models/user/UserRegisterBindingModel';
import {AuthService} from 'src/app/core/services/auth.service';
import {Title} from "@angular/platform-browser";
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css', '../../index/index.component.css']
})
export class UserRegisterComponent implements OnInit {
  private userRegisterBindingModel: UserRegisterBindingModel;
  private isUserRegistered: boolean;

  constructor(private authService: AuthService,
              private router: Router,
              private title: Title) {
  }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Log In or Register');
    this.userRegisterBindingModel = new UserRegisterBindingModel();
    this.isUserRegistered = false;
  }

  onSubmit() {
    let formData = new FormData();

    let userBlobModel = new Blob(
      [JSON.stringify(this.userRegisterBindingModel)], {type: 'application/json'}
    );

    formData.append("registerModel", userBlobModel);

    this.authService.registerUser(formData).subscribe(data => {
      this.userRegisterBindingModel = data['rejectedModel'];

      if (!data['errors']) {
        this.isUserRegistered = true;
      } else {
        //todo Bind errors to the validation.
        // NgModel.bind("errors", data['errors'])
      }

      console.log(this.userRegisterBindingModel);
      console.log(data['errors']);

      if (this.isUserRegistered) {
        /*todo fill rejected model back into the form*/
        console.log("navigating");
        this.router.navigate(['/user/login']);
      }

    });
  }


}
