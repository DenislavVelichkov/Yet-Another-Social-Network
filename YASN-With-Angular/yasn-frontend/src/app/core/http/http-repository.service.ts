import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";
import {take} from "rxjs/operators";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) {
  }

  public get(route?: string): Observable<Object> {

    return this.http.get(HttpRepositoryService.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }

  public create(route: string, body): Observable<Object> {
    return this.http.post(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {})
      .pipe(take(1));
  }

  public loginRequest(route: string, body: any) {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {headers: headers, observe: "response"}).pipe(take(1));
  }

  public post(route: string, body) {
    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded'
        }),
        observe: "response"
      }
    ).pipe(take(1))
  }

  public update(route: string, body): Observable<Object> {
    return this.http.put(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {})
      .pipe(take(1));
  }

  public delete(route: string): Observable<Object> {
    return this.http.delete(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }


  private static createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }
}
