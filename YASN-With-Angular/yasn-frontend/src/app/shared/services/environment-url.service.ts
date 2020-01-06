import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable()
export class EnvironmentUrlService {
  // @ts-ignore
  public urlAddress: string = environment.urlAddress;

  constructor() { }
}
