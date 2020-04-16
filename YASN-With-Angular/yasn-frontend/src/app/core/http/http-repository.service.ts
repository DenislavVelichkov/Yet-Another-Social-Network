import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {take} from "rxjs/operators";

@Injectable({providedIn: "root"})
export class HttpRepositoryService {

  constructor(private http: HttpClient,
              private envUrl: EnvironmentUrlService) {
  }

  public get(route?: string) {

    return this.http.get(HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress));
  }

  public createUser(route: string, body) {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body);
  }

  public loginRequest(route: string, body: any) {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {headers: headers, observe: "response"});
  }

  public postWithForm(route: string, body) {

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
