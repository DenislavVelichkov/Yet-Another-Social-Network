import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";
import {ActiveUser} from "../models/user/ActiveUser";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) {
  }

  public getData(route: string): Observable<Object> {

    return this.http.get(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress));
  }

  public getActiveUser(route: string): Observable<ActiveUser> {

    return this.http.get<ActiveUser>(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress));
  }

  public create(route: string, body): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeader(body));
  }

  public loginRequest(route: string, body): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers: this.generateHeader(body), observe:"response"});
  }

  public update(route: string, body): Observable<Object> {
    return this.http.put(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeader(body));
  }

  public delete(route: string): Observable<Object> {
    return this.http.delete(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress));
  }

  private generateHeader(body?, specialHeaders?) {
    let headers = new HttpHeaders();

    if (body instanceof FormData) {
      headers.set('Content-Type', 'application/x-www-form-urlencoded;utf-8')
    }

    return specialHeaders? specialHeaders : headers;
  }

  private createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }
}
