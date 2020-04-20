import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {take} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class HttpRepositoryService {
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });
  private formHeaders = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  constructor(private http: HttpClient,
              private envUrl: EnvironmentUrlService) {
  }

  public get(route?: string) {

    return this.http.get(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      {headers: this.headers});
  }

  public create(route: string, body): Observable<Object> {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body);
  }

  public loginRequest(route: string, body: any) {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {headers:this.headers, observe: "response"});
  }

  public postWithForm(route: string, body) {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {
        headers: this.formHeaders,
        observe: "response"
      }
    ).pipe(take(1))
  }

  public update(route: string, body) {
    return this.http.put(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {})
      .pipe(take(1));
  }

  public delete(route: string) {
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
