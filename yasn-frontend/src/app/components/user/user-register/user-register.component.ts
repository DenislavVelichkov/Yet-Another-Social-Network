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
import {CustomSuccessSnackbarComponent} from "../../custom-snackbar/success-snackbar/custom-success-snackbar.component";
import {ErrorSnackbarComponent} from "../../custom-snackbar/error-snackbar/error-snackbar.component";

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
              private store$: Store<AppState>,
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

    formData.append('registerModel', userBlobModel);

    this.auth.registerUser(formData).subscribe({
      next: (data) => {
        this.store$.dispatch(new RegisterAction({
          isRegistered: data['isUserRegistered'],
          loading: true
        }));

        if (!data['isUserRegistered']) {
          this.userRegisterBindingModel = data['rejectedModel'];
          this.errors = [...data['errors']];
          this.errors.forEach(error => {
            this.snackBar.openFromComponent(ErrorSnackbarComponent,
              {
                verticalPosition: "top",
                duration: 3000,
                data: error['defaultMessage'],
              }
            );
          });
          this.router.navigate(['/']).catch(reason => console.log(throwError(()=> new Error(reason))));
        } else {
          this.store$.dispatch(new RegisterSuccessAction({
            isRegistered: data['isUserRegistered'],
            loading: false
          }));
          this.snackBar.openFromComponent(CustomSuccessSnackbarComponent,
            {
              verticalPosition: "top",
              duration: 3000,
              data: "Registration Successful! You can  enter your credentials and Log In now.",
            }
          );
        }
      },
      error: (error) => {
        this.errors = error;
        console.log(new Error(error));
      }
    });
  }
}
