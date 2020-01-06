import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../shared/services/http-repository.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpRepo: HttpRepositoryService) { }
}
