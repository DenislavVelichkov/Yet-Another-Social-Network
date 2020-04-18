import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpRepositoryService} from "../../http/http-repository.service";

@Injectable({providedIn: "root"})
export class UserService {

    constructor(private httpRepo: HttpRepositoryService) { }

    registerUser(formData: FormData): Observable<Object> {
        return this.httpRepo.create("/api/user/register", formData);
    }
}
