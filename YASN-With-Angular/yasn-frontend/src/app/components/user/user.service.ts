import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppSettings} from 'src/app/shared/common/constants/AppSettings';
import {Observable} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private httpClient: HttpClient) { }

    registerUser(formData: FormData): Observable<Object> {
        return this.httpClient.post(`${AppSettings.API_ENDPOINT_URL}/user/register`, formData);
    }
}
