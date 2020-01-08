import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";
import {ActiveUser} from "../models/user/ActiveUser";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) { }

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

  public create(route: string, body): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
  }

  public loginRequest(route: string, body, headers): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers, responseType: "text", withCredentials: true, observe:"response"});
  }

  public update(route: string, body): Observable<Object> {
    return this.http.put(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
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

  private generateHeaders(body) {
    if (body instanceof FormData) {
      return;
    }

    const headers = [
      {
        'Content-Type': 'application/json'
      },
    ];

    return {
      headers: new HttpHeaders(...headers)
    };
  }
}
