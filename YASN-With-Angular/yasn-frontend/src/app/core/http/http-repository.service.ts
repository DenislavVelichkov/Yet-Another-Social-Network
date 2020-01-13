import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";
import {ActiveUser} from "../store/authentication/ActiveUser";
import {take} from "rxjs/operators";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) {
  }

  public getData(route?: string): Observable<Object> {

    return this.http.get(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }

  public getActiveUser(route: string): Observable<ActiveUser> {

    return this.http.get<ActiveUser>(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }

  public create(route: string, body): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeader(body))
      .pipe(take(1));
  }

  public loginRequest(route?: string, body?: any): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {headers: this.generateHeader(body), observe: "response"})
      .pipe(take(1));
  }

  public update(route: string, body): Observable<Object> {
    return this.http.put(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      this.generateHeader(body))
      .pipe(take(1));
  }

  public delete(route: string): Observable<Object> {
    return this.http.delete(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }

  private generateHeader(body?, specialHeaders?) {
    let headers = new HttpHeaders();

    if (body instanceof FormData) {
      headers.set('Content-Type', 'application/x-www-form-urlencoded;utf-8')
    }

    return specialHeaders ? specialHeaders : headers;
  }

  private createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }
}
