import {Component, OnInit} from '@angular/core';
import {UserRegisterModel} from 'src/app/shared/models/user/UserRegisterModel';
import {Title} from "@angular/platform-browser";
import {Router} from '@angular/router';
import {AppState} from "../../../core/store/app.state";
import {Store} from "@ngrx/store";
import {RegisterAction} from "../../../core/store/authentication/actions/register.action";
import {RegisterSuccessAction} from "../../../core/store/authentication/actions/register-success.action";
import {throwError} from "rxjs";
import {AuthService} from "../../../core/services/authentication/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomSnackbarComponent} from "../../custom-snackbar/custom-snackbar.component";

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css', '../../index/index.component.css']
})
export class UserRegisterComponent implements OnInit {
  userRegisterBindingModel: UserRegisterModel;
  private errors: Array<Object>;

  constructor(private auth: AuthService,
              private router: Router,
              private title: Title,
              private store: Store<AppState>,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.title.setTitle('YASN ' + 'Log In or Register');
    this.userRegisterBindingModel = new UserRegisterModel();
  }

  onSubmit() {
    let formData = new FormData();

    let userBlobModel = new Blob(
      [JSON.stringify(this.userRegisterBindingModel)],
      {type: 'application/json'}
    );

    formData.append("registerModel", userBlobModel);

    this.auth.registerUser(formData).subscribe(data => {

      this.store.dispatch(new RegisterAction({
        isRegistered: data['isUserRegistered'],
        loading: true
      }));

      if (!data['isUserRegistered']) {
        this.userRegisterBindingModel = data['rejectedModel'];
        this.errors = [...data['errors']];
        //todo Display errors in a modal of some kind
        this.errors.forEach(error => alert(error['defaultMessage']));
        this.router.navigate(['/']).catch(reason => console.log(throwError(reason)));
      } else {
        this.store.dispatch(new RegisterSuccessAction({
          isRegistered: data['isUserRegistered'],
          loading: false
        }));
        this.snackBar.openFromComponent(CustomSnackbarComponent,
          {
            verticalPosition: "top",
            duration: 4200,
            data: "Registration Successful! You can Log In now.",
          })
        this.router.navigate(['/user/login']).catch(reason => console.log(throwError(reason)));
      }
    }, error => {
      this.errors = error;
      return console.log(throwError(error));
    });

  }
}
