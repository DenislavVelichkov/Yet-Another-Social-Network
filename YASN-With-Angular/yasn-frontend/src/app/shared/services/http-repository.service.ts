import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";
import {ActiveUser} from "../models/user/ActiveUser";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) {
  }

  public getData(route: string, headers): Observable<Object> {

    return this.http.get(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress), {headers: headers});
  }

  public getActiveUser(route: string, headers): Observable<ActiveUser> {

    return this.http.get<ActiveUser>(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress), {headers: headers});
  }

  public create(route: string, body, headers): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers});
  }

  public loginRequest(route: string, body, headers): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers, observe: "response"});
  }

  public update(route: string, body, headers): Observable<Object> {
    return this.http.put(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers});
  }

  public delete(route: string): Observable<Object> {
    return this.http.delete(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress));
  }

  private createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }
}
