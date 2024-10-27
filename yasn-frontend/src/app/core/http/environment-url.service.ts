import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';

@Injectable({providedIn: 'root'})
export class EnvironmentUrlService {
  public apiEndPointAddress: string = environment.apiEndPointAddress;
  public websocketSockJSFactory: string = environment.websocketSockJSFactory;
  public websocketStompFactory: string = environment.websocketStompFactory;

  constructor() {
  }
}
