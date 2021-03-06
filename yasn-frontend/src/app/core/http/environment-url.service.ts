import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable({providedIn: "root"})
export class EnvironmentUrlService {
  public uiUrlAddress: string = environment.uiUrlAddress;
  public apiEndPointAddress: string = environment.apiEndPointAddress;

  constructor() {
  }
}
