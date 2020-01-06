import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpRepositoryService} from "../../shared/services/http-repository.service";

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private httpRepo: HttpRepositoryService) { }

    registerUser(formData: FormData): Observable<Object> {
        return this.httpRepo.create("/user/register", formData);
    }
}
