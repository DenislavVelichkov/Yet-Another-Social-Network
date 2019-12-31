import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BackendURL} from 'src/app/shared/common/BackendURL';
import {Observable} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(private httpClient: HttpClient) { }

    registerUser(formData: FormData): Observable<Object> {
        return this.httpClient.post(`${BackendURL.URL}/user/register`, formData);
    }
}
