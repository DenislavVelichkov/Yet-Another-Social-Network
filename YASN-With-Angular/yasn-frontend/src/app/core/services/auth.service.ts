import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BackendURL } from 'src/app/shared/common/BackendURL';
import { Observable } from 'rxjs';
import { UserRegisterBindingModel } from 'src/app/shared/models/user/UserRegisterBindingModel';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(private httpClient: HttpClient, private router: Router) { }

    registerUser(formData: FormData): Observable<Object> {
        return this.httpClient.post(`${BackendURL.URL}/user/register`, formData);
    }
}